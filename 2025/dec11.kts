import kotlin.io.path.Path
import kotlin.io.path.readLines

val edges = Path(args[0]).readLines().associate {
    Pair(it.split(":")[0], it.split(": ")[1].split(" "))
}

val seen = mutableMapOf<Pair<String, String>,Long>()
fun findNumPaths(start: String, end: String): Long {
    if (start == end) return 1
    if (start !in edges) return 0
    seen[Pair(start, end)]?.let { return it }
    seen[Pair(start, end)] = edges[start]!!.sumOf { findNumPaths(it, end) }
    return seen[Pair(start, end)]!!
}

val ans1 = findNumPaths("you", "out")
var ans2 = findNumPaths("svr", "fft") * findNumPaths("fft", "dac") * findNumPaths("dac", "out")
ans2 += findNumPaths("svr", "dac") * findNumPaths("dac", "fft") * findNumPaths("fft", "out")
println("ans1: $ans1 ans2: $ans2")