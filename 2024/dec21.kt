import kotlin.io.path.Path
import kotlin.io.path.readLines

fun main(args: Array<String>) {
    val codes: List<String> = Path(args[0]).readLines()
    val numMap = listOf(listOf('7', '8', '9'), listOf('4', '5', '6'), listOf('1', '2', '3'), listOf('#', '0', 'A'))
    val dirMap = listOf(listOf('#', '^', 'A'), listOf('<', 'v', '>'))
    val numDict = mapOf(
        Pair('7', Pair(0,0)), Pair('8', Pair(1,0)), Pair('9', Pair(2,0)),
        Pair('4', Pair(0,1)), Pair('5', Pair(1,1)), Pair('6', Pair(2,1)),
        Pair('1', Pair(0,2)), Pair('2', Pair(1,2)), Pair('3', Pair(2,2)),
        Pair('0', Pair(1,3)), Pair('A', Pair(2,3))
    )
    val dirDict = mapOf(
        Pair('^', Pair(1,0)), Pair('A', Pair(2,0)),
        Pair('<', Pair(0,1)), Pair('v', Pair(1,1)), Pair('>', Pair(2,1))
    )

    val ans1 = codes.sumOf { code ->
        val cn = code.filter { it.isDigit() }.toLong()
        var seq1 = findSeq(code, numDict, numMap)
        val minL1 = seq1.minOfOrNull { it.length }
        seq1 = seq1.filter { it.length == minL1 }

        var seq2 = seq1.flatMap{ seq -> findSeq(seq, dirDict, dirMap) }
        val minL2 = seq2.minOfOrNull { it.length }
        seq2 = seq2.filter { it.length == minL2 }

        val ins = seq2.flatMap{ seq -> findSeq(seq, dirDict, dirMap) }
        val minL3 = ins.minOfOrNull { it.length }!!
        println("code: $code, complexity: ${cn * minL3}")
        cn * minL3
    }
    val ans2 = 0

    println("Ans 1: $ans1"); println("Ans 2: $ans2")
}

private fun findSeq(
    code: String, dict: Map<Char, Pair<Int, Int>>, map: List<List<Char>>
): List<String> {
    var prev = 'A'; var ans = ""; var all = listOf("")
    code.forEachIndexed { i, c ->
        val ins = findShortest(dict[prev]!!, dict[c]!!, map)
        all = all.flatMap { a -> when (i) {
            0 -> ins.map { b -> a + b }
            code.length - 1 -> ins.map { b -> a + "A" +  b + "A" }
            else -> ins.map { b -> a + "A" + b }
        } }
        ans += ins[0] + "A"; prev = c
    }
    return all
}

private val dirs = listOf(
    Triple(0,-1, '^'), Triple(1,0, '>'),
    Triple(0,1, 'v'), Triple(-1,0, '<'))
private fun findShortest(s: Pair<Int, Int>, e: Pair<Int, Int>, map: List<List<Char>>): List<String> {
    val queue = mutableListOf(Triple(s.first, s.second, ""))
    val seen: MutableSet<Pair<Int, Int>> = mutableSetOf()
    val ans: MutableList<String> = mutableListOf(); var best: Int? = null
    while (queue.isNotEmpty()) {
        val (x,y,p) = queue.removeFirst()
        if (x !in 0..<map[0].size || y !in map.indices) continue
        if (map[y][x] == '#' || (best != null && p.length > best)) continue
        seen += Pair(x,y)
        if (x == e.first && y == e.second) { if (best == null) best = p.length; ans += p }
        dirs.forEach { (dx, dy, dc) -> queue.addLast(Triple(x+dx, y+dy, p + dc))}
    }
    return ans
}