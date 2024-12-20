import kotlin.io.path.Path
import kotlin.io.path.readLines

fun main(args: Array<String>) {
    val map: List<MutableList<Char>> = Path(args[0]).readLines().map { it.toMutableList() }
    var sPos = Pair(-1, -1); var ePos = Pair(-1, -1)
    for (x in 0..<map[0].size) { for(y in map.indices) {
        if (map[y][x] == 'S') sPos = Pair(x,y)
        if (map[y][x] == 'E') ePos = Pair(x,y)
    } }

    val (_, path) = findShortest(sPos, ePos, map)
    val ans1 = findNumCheats(sPos, path, map, false, 2)
    println("Ans 1: $ans1")
    val ans2 = findNumCheats(sPos, path, map, true, 20)
    println("Ans 2: $ans2")
}

private val dirs = listOf(Pair(1,0), Pair(-1,0), Pair(0,1), Pair(0,-1))
private val seenDists: MutableMap<Pair<Pair<Int, Int>, Pair<Int, Int>>, Int> = mutableMapOf()
fun findShortest(sPos: Pair<Int, Int>, ePos: Pair<Int, Int>, map: List<List<Char>>): Pair<Int, List<Pair<Int, Int>>> {
    val queue:MutableList<Triple<Pair<Int, Int>, Int, List<Pair<Int, Int>>>>
        = mutableListOf(Triple(sPos, 0, listOf()))
    val seen: MutableSet<Pair<Int, Int>> = mutableSetOf()
    if (Pair(sPos, ePos) in seenDists) return Pair(seenDists[Pair(sPos, ePos)]!!, listOf())

    while (queue.isNotEmpty()) {
        val (pos,d,path) = queue.removeFirst()
        val (x,y) = pos
        if (x !in 0..map[0].size || y !in map.indices) continue
        if (Pair(x,y) in seen || map[y][x] == '#') continue
        if (x == ePos.first && y == ePos.second) {
            seenDists[Pair(sPos, ePos)] = d
            return Pair(d, path)
        }
        seenDists[Pair(sPos, pos)] = d
        seen += Pair(x,y)
        dirs.forEach { (dx, dy) ->
            queue.addLast(Triple(Pair(x+dx, y+dy), d+1, path + Pair(x, y)))
        }
    }
    seenDists[Pair(sPos, ePos)] = -1
    return Pair(-1, listOf())
}

private fun findOther(
    s: Pair<Int, Int>, m: List<List<Char>>, l: Int
): List< Pair< Pair<Int, Int>, Int> > {
    val queue:MutableList<Pair<Pair<Int, Int>, Int>> = mutableListOf(Pair(s, 0))
    val seen: MutableSet<Pair<Int, Int>> = mutableSetOf()
    val toRet: MutableList<Pair<Pair<Int, Int>, Int>> = mutableListOf()

    while (queue.isNotEmpty()) {
        val (pos,d) = queue.removeFirst()
        val (x,y) = pos
        if (x !in 1..<m[0].size-1 || y !in 1..<m.size-1) continue
        if (Pair(x,y) in seen || d>l) continue
        if (m[y][x] != '#') toRet += Pair(Pair(x,y),d)
        seen += Pair(x,y)
        dirs.forEach { (dx, dy) ->
            queue.addLast(Pair(Pair(x+dx, y+dy), d+1))
        }
    }
    return toRet
}

private fun findNumCheats(
    sPos: Pair<Int, Int>, path: List<Pair<Int, Int>>,
    map: List<List<Char>>, print: Boolean, cLim: Int
): Long {
    return path.indices.sumOf { i ->
        val p = path[i]
        if (print && (i+1) % 3000 == 0) println("${i+1}/${path.size}")
        val (d1,_) = findShortest(sPos, p, map)
        val other = findOther(p,map,cLim)
        other.sumOf { (o,d) ->
            val (d2,_) = findShortest(sPos, o, map)
            if (d1 + d <= d2-100) 1 else 0L
        }
    }
}