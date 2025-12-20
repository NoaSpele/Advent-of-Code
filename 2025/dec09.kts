import kotlin.io.path.Path
import kotlin.io.path.readLines
import kotlin.math.absoluteValue

data class P(val x: Long, val y: Long) {
    fun area(p2: P): Long {
        return ((this.x - p2.x).absoluteValue + 1) * ((this.y - p2.y).absoluteValue + 1)
    }
}
val points = Path(args[0]).readLines().map { it.split(",")
    .map { s -> s.toLong() } }.map { P(it[0],it[1]) }

val ans1 = (0..<points.size).flatMap { idx1 -> (idx1+1..<points.size).map { idx2 ->
    points[idx1].area(points[idx2])
} }.max()

// Coordinate compression, unique x/y values in points + outside layer
val xr = points.map { it.x }.toSet().toList().sorted()
val yr = points.map { it.y }.toSet().toList().sorted()
val pr = points.map { P(xr.indexOf(it.x).toLong() + 1, yr.indexOf(it.y).toLong() + 1) }
val lines = (0..<pr.size).map { Pair(pr[it], pr[(it+1).mod(pr.size)]) }
val mr = (0..xr.size+1).map { (0..yr.size+1).map { 0 }.toMutableList() }

lines.forEach { (p1, p2) ->
    (minOf(p1.x, p2.x)..maxOf(p1.x, p2.x)).forEach { x ->
        (minOf(p1.y, p2.y)..maxOf(p1.y, p2.y)).forEach { y ->
            mr[y.toInt()][x.toInt()] = 1
        }
    }
}

// Flood fill outside points, we know (0,0) is outside
val outside = mutableSetOf<Pair<Int, Int>>()
val q = ArrayDeque(listOf(Pair(0, 0)))
while (!q.isEmpty()) {
    val (x,y) = q.removeFirst()
    if (x to y in outside || x !in 0..<mr[0].size || y !in 0..<mr.size || mr[y][x] == 1) continue
    outside += x to y
    q.addAll(listOf(x+1 to y, x-1 to y, x to y+1, x to y-1))
}

// Assuming no holes which was verified by inspecting input
// Area is inside if all sides are inside shape
fun check(p1: P, p2: P): Boolean {
    val sx = minOf(p1.x, p2.x); val lx = maxOf(p1.x, p2.x)
    val sy = minOf(p1.y, p2.y); val ly = maxOf(p1.y, p2.y)
    return (sx..lx).all { x -> (sy..ly).all { y-> (x.toInt() to y.toInt()) !in outside } }
}

val ans2 = (0..<pr.size).flatMap { idx1 -> (idx1+1..<pr.size).map { idx2 ->
    val p1 = pr[idx1]; val p2 = pr[idx2]
    val p3 = P(p1.x, p2.y); val p4 = P(p2.x, p1.y)
    if (!check(p1, p3) || !check(p1, p4) || !check(p2, p3) || !check(p2, p4)) -1
    else points[idx1].area(points[idx2])
} }.max()

println("ans1: $ans1 ans2: $ans2")