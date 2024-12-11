import kotlin.io.path.Path
import kotlin.io.path.readText

fun main(args: Array<String>) {
    var nums: MutableList<Long> = Path(args[0]).readText().split(' ').map { it.toLong() }.toMutableList()

    (0..<25).forEach { idx ->
        val newNums: MutableList<Long> = mutableListOf()
        // println("idx: ${idx+1}, #: ${nums.size}, nums: $nums")
        nums.forEach {num ->
            if (num == 0L) newNums += 1
            else if (num.toString().length  % 2 == 0) {
                val halfIdx = num.toString().length / 2
                newNums += num.toString().substring(0, halfIdx).toLong()
                newNums += num.toString().substring(halfIdx, halfIdx*2).toLong()
            } else {
                newNums += num * 2024
            }
        }
        // println(newNums)
        nums = newNums.toMutableList()
    }

    val ans1 = nums.size
    val ans2 = 0

    println("Ans 1: $ans1")
    println("Ans 2: $ans2")
}