import kotlin.io.path.Path
import kotlin.io.path.readLines

private val dirs = listOf(Pair(1,0), Pair(-1,0), Pair(0,1), Pair(0,-1))
fun main(args: Array<String>) {
    val lines: List<String> = Path(args[0]).readLines()
    val bytes = lines.map { Pair(
        it.split(",")[0].toInt(),
        it.split(",")[1].toInt()
    ) }
    var ans2: Pair<Int, Int> = Pair(-1, -1)
    println("Ans 1: ${getShortestPath(bytes.subList(0, 1024))}")

    var i = 1025; var found = false
    while (true) {
        val dist = getShortestPath(bytes.subList(0,i))
        if (dist == -1 && !found) found = true
        else if (found && dist != -1) { ans2 = bytes[i]; break }
        if (found) i -= 1 else i += 50
    }
    println("Ans 2: ${ans2.first},${ans2.second}")
}

fun getShortestPath(bytes: List<Pair<Int, Int>>): Int {
    var dist = -1
    val map = (0..<71).map { y -> (0..71).map { x ->
        Pair(x,y) !in bytes
    } }
    val sx = 0; val sy = 0
    val seen: MutableSet<Pair<Int, Int>> = mutableSetOf()
    val queue = mutableListOf(Triple(sx, sy, 0))
    while (queue.isNotEmpty()) {
        val (x,y,d) = queue.removeFirst()
        if (x !in 0..<71 || y !in 0..<71) continue
        if (Pair(x,y) in seen || !map[y][x]) continue
        if (x == 70 && y == 70) {
            dist = d; break
        }
        seen.add(Pair(x,y))
        dirs.forEach { (dx, dy) ->
            queue.addLast(Triple(x+dx, y+dy, d+1))
        }
    }
    return dist
}