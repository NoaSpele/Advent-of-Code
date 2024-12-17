import kotlin.io.path.Path
import kotlin.io.path.readText

/*
1: B = A % 8 (0 .. 7)
2: B = B xor 5 (101) (0: 5, 1: 4, 2: 7, 3: 6, 4: 1, 5: 0, 6: 3, 7: 2)
3: C = A / pow(2, B) 0 - 128
4: B = B xor 6 (110) (0: 6, 1: 7, 2: 4, 3: 5, 4: 2, 5: 3, 6: 0, 7: 1)
5: A /= 8
6: B = B xor C
7: print(B)
8: go back to 1 (unless A = 0)
*/

/*
Answer has to be between 8**15 - 8**16-1 = 16 outputs
Low:   35184372088832 (8**15)
High:  281474976710655 (8**16-1)
 */

fun main(args: Array<String>) {
    val (registers, insStr) = Path(args[0]).readText().split("\n\n")
    val regA = registers.split("\n")[0].split(": ")[1].toLong()
    val ins = insStr.split(": ")[1].split(',').map { it.toLong() }

    val ans1 = run(regA, ins, 1)
    println("Ans 1: $ans1")

    for (i in pow(8,0)..pow(8,11)) {
        // First sequence for 7+ correct
        // val curr = run(i*pow(8,8) + 3287450, ins, 2) // ...14424632
        // First sequence for 10+ correct
        val curr = run(i*pow(8,9) + 18966938, ins, 2) // ...110264632
        if (correct.joinToString(",") == curr) {
            println("Ans 2: ${i*pow(8,9) + 18966938}"); break
        }
        // Used to find repeating sequence
        // run(i, ins, 2)
    }
}

val correct: List<Long> = listOf(2,4,1,5,7,5,1,6,0,3,4,2,5,5,3,0)
var best = 0
fun run(a: Long, ins: List<Long>, part: Int): String {
    var regA = a; var regB = 0L; var regC = 0L
    var pointer = 0
    val out: MutableList<Long> = mutableListOf()
    while (pointer < ins.size-1) {
        val currIns = ins[pointer]
        val operand = ins[pointer + 1]
        var combo: Long
        if (operand == 4L) combo = regA
        else if (operand == 5L) combo = regB
        else if (operand == 6L) combo = regC
        else combo = operand
        if (currIns == 0L) regA /= pow(2L, combo)
        else if (currIns == 1L) regB = regB xor operand
        else if (currIns == 2L) regB = combo % 8
        else if (currIns == 3L) {
            if (regA != 0L) {
                pointer = operand.toInt()
                continue
            }
        } else if (currIns == 4L) regB = regB xor regC
        else if (currIns == 5L) {
            out += combo % 8
            if (part == 2 && out == correct) {
                return out.joinToString(",")
            }
            if (part == 2 && out != correct.subList(0,out.size)) {
                return ""
            } /* else if (part == 2 && out.size >= best) {
                println("A: $a, a octo: ${"%o".format(a)} out: $out")
                best = out.size
            } */
        }
        else if (currIns == 6L) regB = regA / pow(2L, combo)
        else if (currIns == 7L) regC = regA / pow(2L, combo)
        pointer += 2
    }
    return out.joinToString(",")
}

fun pow(a: Long, b: Long): Long {
    if (b == 0L) return 1L
    if (b == 1L) return a
    else {
        var ans = a
        (1..<b).forEach { _ -> ans *= a }
        return ans
    }
}