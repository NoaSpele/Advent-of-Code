import kotlin.io.path.Path
import kotlin.io.path.readLines

fun main(args: Array<String>) {
    val lines: List<String> = Path(args[0]).readLines()
    var ans1 = 0L; var ans2 = 0L
    lines.forEach {
        val test = it.split(':')[0].toLong()
        val vals = it.split(':')[1].trim().split(' ')
            .map { num -> num.toLong() }
        if (testVals(test, vals, 0L, 1) == test) ans1 += test
        if (testVals(test, vals, 0L, 2) == test) ans2 += test
    }
    println("Ans 1: $ans1")
    println("Ans 2: $ans2")
}

fun testVals(test: Long, vals: List<Long>, sum: Long, part: Int): Long {
    if (vals.isEmpty() && test == sum) return test
    if (sum > test || vals.isEmpty()) return 0L
    if (
        testVals(test, vals.subList(1,vals.size), sum * vals[0], part) == test
        || testVals(test, vals.subList(1,vals.size), sum + vals[0], part) == test
        || (part == 2 && testVals(
            test, vals.subList(1,vals.size), "$sum${vals[0]}".toLong(), part
        ) == test)
    ) return test
    return 0L
}