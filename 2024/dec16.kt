import java.util.PriorityQueue
import kotlin.io.path.Path
import kotlin.io.path.readLines

private val dirs = listOf(
    DirInv(1,0,0,1),
    DirInv(-1,0,1,0),
    DirInv(0,1,2,3),
    DirInv(0,-1,3,2))
data class DirInv (val dx: Int, val dy: Int, val dir: Int, val inv: Int)
data class Data (val x: Int, val y: Int, val dir: Int, val c: Int, val p: String)
fun main(args: Array<String>) {
    val map: List<List<Char>> = Path(args[0]).readLines().map { it.toList() }
    var sPos = Pair(-1, -1); var ePos = Pair(-1, -1)
    for (x in 0..<map[0].size) { for(y in map.indices) {
        if (map[y][x] == 'S') sPos = Pair(x,y)
        if (map[y][x] == 'E') ePos = Pair(x,y)
    } }

    val ans1 = findCheapest(sPos, ePos, map, null).first
    println("Ans 1: $ans1")

    val bestPaths = findCheapest(sPos, ePos, map, ans1).second
    val ans2 = bestPaths.flatMap { it.substring(1,it.length).split(";").map { str ->
        Pair(str.split(",")[0].toInt(), str.split(",")[1].toInt())
    } }.toSet().size
    println("Ans 2: $ans2")
}

fun findCheapest(
    sPos: Pair<Int, Int>, ePos: Pair<Int, Int>, map: List<List<Char>>, best: Int?
): Pair<Int?, List<String>> {
    val paths: MutableList<String> = mutableListOf()
    val queue = PriorityQueue<Data> { a, b -> a.c - b.c }
    queue.offer(Data(sPos.first, sPos.second, -1, 0, ""))
    val seen: MutableMap<String, Int> = mutableMapOf()
    while (queue.isNotEmpty()) {
        val (x,y,dir,c,p) = queue.poll()
        if (x !in 0..<map[0].size || y !in map.indices || map[y][x] == '#') continue
        val currSeen = seen["$x,$y,$dir"]
        if (currSeen != null && (best == null || currSeen < c)) continue
        seen["$x,$y,$dir"] = c
        if (x == ePos.first && y == ePos.second) {
            if (best == null ) return Pair(c, listOf())
            if (best == c) paths += "$p;$x,$y"
            else return Pair(-1, paths)
        }
        dirs.forEach { (dx, dy, d, i) ->
            if (dir != i) {
                val toAdd = if (d == dir) 1 else 1001
                queue.offer(Data(x+dx, y+dy, d, c + toAdd, "$p;$x,$y"))
            }
        }
    }
    return Pair(-1, listOf())
}