import kotlin.io.path.Path
import kotlin.io.path.readLines

fun main(args: Array<String>) {
    val lines: List<String> = Path(args[0]).readLines()
    val reg1 = Regex("mul\\(\\d{1,3},\\d{1,3}\\)")
    val reg2 = Regex("(mul\\(\\d{1,3},\\d{1,3}\\))|(do\\(\\))|(don't\\(\\))")

    println("Ans 1: ${calSum(lines, reg1, false)}")
    println("Ans 2: ${calSum(lines, reg2, true)}")
}

fun calSum(lines: List<String>, reg: Regex, p2: Boolean): Int {
    var sum = 0
    var enabled = true
    lines.forEach { line ->
        reg.findAll(line).iterator()
            .forEach {
                if (p2 && it.value == "do()") enabled = true
                else if (p2 && it.value == "don't()") enabled = false
                else if (enabled) {
                    val nums = it.value.split(',')
                    val a = Regex("\\d{1,3}").find(nums[0])?.value?.toInt()
                    val b = Regex("\\d{1,3}").find(nums[1])?.value?.toInt()
                    if (a != null && b != null) sum += a * b
                }
            }
    }
    return sum
}