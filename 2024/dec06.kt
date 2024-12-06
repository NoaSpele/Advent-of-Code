import kotlin.io.path.Path
import kotlin.io.path.readLines

data class Pos (val x: Int, val y: Int)
data class DirPos (val x: Int, val y: Int, val dx: Int, val dy: Int)

fun main(args: Array<String>) {
    val map: List<List<Char>> = Path(args[0]).readLines().map { it.toList() }
    val sy = map.indexOf(map.find { row -> row.contains('^') })
    val sx = map[sy].indexOf('^')

    val ans1Set = checkPath(sx, sy, map)
    println("Ans 1: ${ans1Set.size}")

    var ans2 = 0; var numChecked = 0
    ans1Set.forEach { p ->
        val mapCopy = map.map { it.toMutableList() }
        mapCopy[p.y][p.x] = '#'
        if (checkPath(sx, sy, mapCopy).size == 0) ans2 += 1
        numChecked += 1
        if (numChecked % 1000 == 0) println("Num checked: $numChecked")
    }
    println("Ans 2: $ans2")
}

fun checkPath(sx: Int, sy: Int, map: List<List<Char>>): MutableSet<Pos> {
    val visited: MutableSet<Pos> = mutableSetOf()
    val loopSet: MutableSet<DirPos> = mutableSetOf()
    val w = map[0].size; val h = map.size
    var x = sx; var y = sy; var dir = arrayOf(0, -1)

    while (true) {
        if (Pos(x,y) !in visited) visited += Pos(x,y)
        if (DirPos(x,y,dir[0],dir[1]) in loopSet) return mutableSetOf()
        else loopSet += DirPos(x,y,dir[0],dir[1])
        val dx = dir[0]; val dy = dir[1]
        var tx = x + dx; var ty = y + dy
        if (tx < 0 || tx > w-1 || ty < 0 || ty > h-1) break
        while (map[ty][tx] == '#') {
            dir = changeDirection(dir[0], dir[1])
            tx = x + dir[0]; ty = y + dir[1]
        }
        x = tx; y = ty
    }
    return visited
}

fun changeDirection(dx: Int, dy: Int): Array<Int> {
    if (dx == 0 && dy == -1) return arrayOf(1,0)
    else if (dx == 1 && dy == 0) return arrayOf(0,1)
    else if (dx == 0 && dy == 1) return arrayOf(-1,0)
    else return arrayOf(0,-1)
}