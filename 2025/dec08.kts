import kotlin.io.path.Path
import kotlin.io.path.readLines
import kotlin.math.max
import kotlin.math.min

data class P3 (val x: Long, val y: Long, val z: Long) {
    fun dist(p2: P3): Long {
        val dx = this.x - p2.x
        val dy = this.y - p2.y
        val dz = this.z - p2.z
        return dx*dx + dy*dy + dz*dz
    }
}

val points = Path(args[0]).readLines().map {
    it.split(",").map { s -> s.toLong() }.let { p-> P3(p[0], p[1], p[2]) }
}
val sorted = (0..<points.size).flatMap { i1 -> (i1+1..<points.size).map { i2 ->
    val p1 = points[i1]; val p2 = points[i2]
    Triple(p1, p2, p1.dist(p2))
} }.sortedBy { it.third }

var ans1 = -1; var ans2 = -1L
val circuits = mutableListOf<MutableList<P3>>()
for (it in 0..<sorted.size) {
    val curr = sorted[it]
    val p1InC = circuits.withIndex().firstOrNull{ c-> curr.first in c.value }
    val p2InC = circuits.withIndex().firstOrNull{ c-> curr.second in c.value }
    if (p1InC == null && p2InC == null) circuits += mutableListOf(curr.first, curr.second)
    else if (p1InC == null) circuits[p2InC!!.index] += curr.first
    else if (p2InC == null) circuits[p1InC.index] += curr.second
    else if (p1InC.index != p2InC.index) {
        val maxIdx = max(p1InC.index, p2InC.index)
        val minIdx = min(p1InC.index, p2InC.index)
        val toAdd = circuits[maxIdx].toMutableList()
        circuits.remove(circuits[maxIdx])
        circuits[minIdx].addAll(toAdd)
    }

    if (it == args[1].toInt()-1) {
        ans1 = circuits.map { it.size }.sorted().reversed().let { it[0]*it[1]*it[2] }
    }

    if (circuits.any { it.size == points.size }) {
        ans2 = curr.first.x * curr.second.x
        break
    }
}

println("ans1: $ans1 ans2: $ans2")