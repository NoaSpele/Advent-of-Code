import kotlin.io.path.Path
import kotlin.io.path.readLines
import kotlin.math.abs

fun main(args: Array<String>) {
    val map: List<MutableList<Char>> = Path(args[0]).readLines().map { it.toMutableList() }
    var sPos = Pair(-1, -1); var ePos = Pair(-1, -1)
    for (x in 0..<map[0].size) { for(y in map.indices) {
        if (map[y][x] == 'S') sPos = Pair(x,y)
        if (map[y][x] == 'E') ePos = Pair(x,y)
    } }

    val (_, path) = findShortest(sPos, ePos, map)
    val ans1 = findNumCheats(sPos, path, map, 2)
    println("Ans 1: $ans1")
    val ans2 = findNumCheats(sPos, path, map, 20)
    println("Ans 2: $ans2")
}

private val dirs = listOf(
    DirInv(1,0,0,1),
    DirInv(-1,0,1,0),
    DirInv(0,1,2,3),
    DirInv(0,-1,3,2))
private val seenDists: MutableMap<Pair<Pair<Int, Int>, Pair<Int, Int>>, Int> = mutableMapOf()
data class QO(val x: Int, val y: Int, val d: Int, val dir: Int, val p: List<Pair<Int, Int>> )
fun findShortest(sPos: Pair<Int, Int>, ePos: Pair<Int, Int>, map: List<List<Char>>): Pair<Int, List<Pair<Int, Int>>> {
    val queue:MutableList<QO> = mutableListOf(QO(sPos.first, sPos.second, 0, -1, listOf()))
    val seen: MutableSet<Pair<Int, Int>> = mutableSetOf()
    if (Pair(sPos, ePos) in seenDists) return Pair(seenDists[Pair(sPos, ePos)]!!, listOf())

    while (queue.isNotEmpty()) {
        val (x,y,d,dir,path) = queue.removeFirst()
        if (x !in 1..<map[0].size-1 || y !in 1..<map.size-1) continue
        if (Pair(x,y) in seen || map[y][x] == '#') continue
        if (x == ePos.first && y == ePos.second) {
            seenDists[Pair(sPos, ePos)] = d
            return Pair(d, path)
        }
        seenDists[Pair(sPos, Pair(x,y))] = d; seen += Pair(x,y)
        dirs.forEach { (dx, dy, nd, inv) ->
            if (dir != inv) queue.addLast(QO(x+dx, y+dy, d+1, nd,path + Pair(x, y)))
        }
    }
    seenDists[Pair(sPos, ePos)] = -1
    return Pair(-1, listOf())
}

private fun findNumCheats(
    sPos: Pair<Int, Int>, path: List<Pair<Int, Int>>, map: List<List<Char>>, cLim: Int
): Long {
    return path.sumOf { p1 ->
        val (d1,_) = findShortest(sPos, p1, map)
        path.sumOf { p2 ->
            val dist = abs(p1.first - p2.first) + abs(p1.second - p2.second)
            if (p1 != p2 && dist <= cLim) {
                if (d1 + dist <= findShortest(sPos, p2, map).first - 100) 1 else 0
            } else 0L
        }
    }
}