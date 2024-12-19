import kotlin.io.path.Path
import kotlin.io.path.readText

fun main(args: Array<String>) {
    val text: String = Path(args[0]).readText()
    val (p1, p2) = text.split("\n\n")
    val towels = p1.split(", ")
    val patterns = p2.split("\n")

    var ans1 = 0; var ans2 = 0L
    patterns.forEach { pattern ->
        val num = numPossible(pattern, towels)
        ans2 += num
        if (num > 0) ans1 += 1
    }

    println("Ans 1: $ans1")
    println("Ans 2: $ans2")
}

val seen: MutableMap<String, Long> = mutableMapOf()
fun numPossible(pattern: String, towels: List<String>): Long {
    var ans = 0L
    if (pattern in seen) return seen[pattern]!!
    for (t in towels) {
        if (pattern == t) ans += 1
        if (pattern.length > t.length && pattern.substring(0,t.length) == t) {
            ans += numPossible(pattern.substring(t.length, pattern.length), towels)
        }
    }
    seen[pattern] = ans
    return ans
}