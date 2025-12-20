import kotlin.io.path.Path
import kotlin.io.path.readLines

fun findMax(arr: List<Long>, num: Int, ans: String): Long {
    if (num == 1) return (ans + arr.max()).toLong()
    val max = arr.subList(0, arr.size-(num-1)).withIndex().maxBy { it.value }
    return findMax(arr.subList(max.index+1, arr.size), num-1, ans + max.value)
}

val banks = Path(args[0]).readLines().map { it.map { c -> c.digitToInt().toLong() } }
println("ans1: ${banks.sumOf { findMax(it,2,"") }} ans2: ${banks.sumOf { findMax(it,12,"") }}")