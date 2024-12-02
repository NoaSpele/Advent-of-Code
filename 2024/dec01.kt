import kotlin.io.path.Path
import kotlin.io.path.readLines
import kotlin.math.*

fun main(args: Array<String>) {
    val lines: List<List<String>> = Path(args[0]).readLines().map { it.split("\\s+".toRegex()) }
    val l1: List<Int> = lines.map { it[0].toInt() }.sorted()
    val l2: List<Int> = lines.map { it[1].toInt() }.sorted()
    println("Ans 1: " + l1.zip(l2).map { abs(it.first - it.second) }.sum())
    println("Ans 2: " + l1.map { a -> a * l2.count { b -> a == b } }.sum())
}