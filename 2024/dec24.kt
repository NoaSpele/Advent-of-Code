import kotlin.io.path.Path
import kotlin.io.path.readText

fun main(args: Array<String>) {
    val (p1, p2) = Path(args[0]).readText().split("\n\n")
    val vals: MutableMap<String, Boolean> = mutableMapOf()
    var valX = 0L; var valY = 0L
    p1.split("\n").forEach { line ->
        val (name, v) = line.split(": ")
        vals += Pair(name, v.toInt() == 1)
        if ("x\\d+".toRegex().matches(name) && v.toInt() == 1) {
            val exp = name.substring(1,name.length).toLong()
            valX += pow(2,exp)
        }
        if ("y\\d+".toRegex().matches(name) && v.toInt() == 1) {
            val exp = name.substring(1,name.length).toLong()
            valY += pow(2,exp)
        }
    }

    println((valX + valY).toString(2))

    val ans1 = getZ(p2.split("\n").toMutableList(), vals)
    val ans2 = 0

    println(ans1.toString(2))
    println("Ans 1: $ans1")
    println("Ans 2: $ans2")
}

private fun getZ(ops: MutableList<String>, vals: Map<String, Boolean>): Long {
    var toRet = 0L; val valsCopy = vals.toMutableMap()
    while (ops.isNotEmpty()) {
        val line = ops.removeFirst()
        val (input, out) = line.split(" -> ")
        val (a, op, b) = input.split(" ")
        val valA = valsCopy[a]; val valB = valsCopy[b]
        if (valA == null || valB == null) {
            ops += line; continue
        }
        var ans: Boolean? = null
        if (op == "AND") ans = valA && valB
        else if (op == "OR") ans = valA || valB
        else if (op == "XOR") ans = valA != valB

        if ("z\\d+".toRegex().matches(out)) {
            val exp = out.substring(1,out.length).toLong()
            if (ans == true) toRet += pow(2,exp)
        } else {
            valsCopy += Pair(out, ans == true)
        }
    }
    return toRet
}