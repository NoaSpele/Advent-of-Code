import kotlin.io.path.Path
import kotlin.io.path.readLines

val map = Path(args[0]).readLines().map { it.map { c->c } }
val h = map.size; val w = map[0].size; var s = Pair(-1, -1)
for (x in 0..<w) { for (y in 0..<h) {
    if (map[y][x] == 'S') { s = Pair(x,y); break }
}}

fun findNumSplit(): Int {
    var num = 0
    val q = mutableListOf(s)
    val seen = mutableSetOf<Pair<Int, Int>>()
    while (!q.isEmpty()) {
        val (x,y) = q.removeFirst()
        if (x to y in seen || x !in 0..<w || y !in 0..<h) continue
        seen += x to y
        if (map[y][x] == '^') {
            num += 1; q.addAll(listOf(Pair(x-1, y), Pair(x+1, y)))
        } else q += Pair(x, y+1)
    }
    return num
}

val seen = mutableMapOf<Pair<Int, Int>, Long>()
fun numPaths(x: Int, y: Int): Long {
    if (x !in 0..<w || y !in 0..<h) return 1L
    return seen.getOrPut(x to y) {
        if (map[y][x] != '^') numPaths(x, y + 1)
        else numPaths(x - 1, y) + numPaths(x + 1, y)
    }
}
val ans1 = findNumSplit()
val ans2 = numPaths(s.first, s.second)
println("ans1: $ans1 ans2: $ans2")