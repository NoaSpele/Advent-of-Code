import kotlin.io.path.Path
import kotlin.io.path.readLines

fun main(args: Array<String>) {
    val lines: List<String> = Path(args[0]).readLines()
    val reg1 = Regex("mul\\((\\d{1,3}),(\\d{1,3})\\)")
    val reg2 = Regex("mul\\((\\d{1,3}),(\\d{1,3})\\)|do\\(\\)|don't\\(\\)")

    println("Ans 1: ${calSum(lines, reg1, false)}")
    println("Ans 2: ${calSum(lines, reg2, true)}")
}

fun calSum(lines: List<String>, reg: Regex, p2: Boolean): Int {
    var enabled = true
    return lines.sumOf { line ->
        reg.findAll(line).sumOf {
            if (p2 && it.value == "do()") { enabled = true; 0 }
            else if (p2 && it.value == "don't()") { enabled = false; 0 }
            else if (enabled) it.groupValues[1].toInt() * it.groupValues[2].toInt()
            else 0
        }
    }
}