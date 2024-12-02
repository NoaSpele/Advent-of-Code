import kotlin.io.path.Path
import kotlin.io.path.readLines
import kotlin.math.abs

fun main(args: Array<String>) {
    val numSequences: List<List<Int>> = Path(args[0]).readLines()
        .map { seq -> seq.split(' ').map { it.toInt() } }

    val ans1: Int = numSequences.map { getDiffList(it) }.map { if (isSafe(it)) 1 else 0 }.sum()
    val ans2: Int = numSequences.map {
        it.indices.find { i -> isSafe(getDiffList(it.subList(0,i) + it.subList(i+1,it.size))) }
    }.map { if (it != null) 1 else 0 }.sum()

    println("Ans 1: $ans1")
    println("Ans 2: $ans2")
}

fun getDiffList(seq: List<Int>): List<Int> {
    return seq.subList(0, seq.size-1).zip(seq.subList(1, seq.size))
        .map { pair -> pair.first - pair.second }
}

fun isSafe(list: List<Int>): Boolean {
    if (list.contains(0) || list.any { abs(it) > 3 }) return false
    else if (list.any { it > 0 } && list.any { it < 0 }) return false
    else return true
}