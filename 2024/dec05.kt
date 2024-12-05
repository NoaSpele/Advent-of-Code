import kotlin.io.path.Path
import kotlin.io.path.readText

fun main(args: Array<String>) {
    val input: String = Path(args[0]).readText()
    val p1 = input.split("\n\n")[0].split("\n")
    val p2 = input.split("\n\n")[1].split("\n")

    val rules = p1.map { it.split('|').map { num -> num.toInt() } }
    val seqs = p2.map { it.split(",").map { num -> num.toInt() } }

    val ans1 = seqs.filter { isValid(it, rules) }.sumOf { it[it.size / 2] }

    val ans2 = seqs.filter { !isValid(it, rules) }
        .map { correctSeq(it, rules) }.sumOf { it[it.size / 2] }

    println("Ans 1: $ans1")
    println("Ans 2: $ans2")
}

fun isValid(list: List<Int>, rules: List<List<Int>>): Boolean {
    val activeRules = rules.filter { rule ->
        list.contains(rule[0]) && list.contains(rule[1])
    }
    activeRules.forEach { if (list.indexOf(it[1]) < list.indexOf(it[0])) return false }
    return true
}

fun correctSeq(seq: List<Int>, rules: List<List<Int>>): List<Int> {
    val activeRules = rules.filter { rule ->
        seq.contains(rule[0]) && seq.contains(rule[1])
    }
    return seq.sortedWith { a, b ->
        if (activeRules.contains(listOf(a, b))) -1
        else if (activeRules.contains(listOf(b, a))) 1
        else 0
    }
}