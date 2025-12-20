import kotlin.io.path.Path
import kotlin.io.path.readLines

val lines = Path(args[0]).readLines()
val ops = lines.last().trim().split("\\s+".toRegex())
val opIds = lines.last().mapIndexed { i, c ->
    if (c != ' ') i else -1
}.filter { it != -1 }
val numStr = lines.take(lines.size-1)
val nums = numStr.map {
    it.trim().split("\\s+".toRegex()).map { c -> c.toLong() }
}

fun reduceVals(op: String, vals: List<Long>): Long {
    if (op == "*") return vals.reduce { acc, v -> acc * v }
    else return vals.reduce { acc, v -> acc + v }
}

fun findVals(idx1: Int, idx2: Int): List<Long> {
    return (idx1..<idx2).map { idx ->
        numStr.map { if (it[idx].isDigit()) it[idx] else ""}.joinToString("").toLong()
    }
}

val ans1 = ops.withIndex().sumOf {
    reduceVals(it.value, nums.map { curr -> curr[it.index] })
}
val ans2 = ops.mapIndexed { idx, op ->
    val idx2 = if (idx == ops.size-1) lines[0].length else opIds[idx+1]-1
    reduceVals(op, findVals(opIds[idx], idx2))
}.sum()
println("ans1: $ans1 ans2: $ans2")