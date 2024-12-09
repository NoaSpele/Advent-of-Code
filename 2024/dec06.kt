import kotlin.io.path.Path
import kotlin.io.path.readLines

data class DirPos (val x: Int, val y: Int, val dx: Int, val dy: Int)

fun main(args: Array<String>) {
    val map: List<MutableList<Char>> = Path(args[0]).readLines().map { it.toMutableList() }
    val sy = map.indexOf(map.find { row -> row.contains('^') })
    val sx = map[sy].indexOf('^')

    val ans1Set = checkPath(sx, sy, map)
    println("Ans 1: ${ans1Set.size}")

    val ans2 = ans1Set.sumOf { p ->
        map[p.second][p.first] = '#'
        val numPos = checkPath(sx, sy, map).size
        map[p.second][p.first] = '.'
        if (numPos == 0) 1 else 0L
    }
    println("Ans 2: $ans2")
}

fun checkPath(sx: Int, sy: Int, map: List<List<Char>>): MutableSet<Pair<Int, Int>> {
    val visited: MutableSet<Pair<Int, Int>> = mutableSetOf()
    val loopSet: MutableSet<DirPos> = mutableSetOf()
    val w = map[0].size; val h = map.size
    var x = sx; var y = sy; var dirIdx = 3

    while (true) {
        val (dx, dy) = getDirection(dirIdx)
        if (Pair(x,y) !in visited) visited += Pair(x, y)
        if (DirPos(x, y, dx, dy) in loopSet) return mutableSetOf()
        else loopSet += DirPos(x, y, dx, dy)
        val tx = x + dx; val ty = y + dy
        if (tx !in 0..<w || ty !in 0..<h) break
        if (map[ty][tx] == '#') dirIdx = (dirIdx + 1) % 4
        else { x = tx; y = ty }
    }
    return visited
}

data class Dir(val dx: Int, val dy: Int)
val dirList = listOf(Dir(1,0), Dir(0,1), Dir(-1,0), Dir(0,-1))
fun getDirection(dirIdx: Int): Dir { return dirList[dirIdx] }