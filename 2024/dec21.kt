import kotlin.io.path.Path
import kotlin.io.path.readLines
import kotlin.math.abs

fun main(args: Array<String>) {
    val codes: List<String> = Path(args[0]).readLines()
    val ans1 = solve(codes, 1)
    val ans2 = solve(codes, 2)

    println("Ans 1: $ans1"); println("Ans 2: $ans2")
}

private fun solve(codes: List<String>, numRobot: Int): Long {
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

    return codes.sumOf { code ->
        val cn = code.filter { it.isDigit() }.toLong()
        var seq1 = findSeq(code, numDict, numMap)
        val minL1 = seq1.minOfOrNull { calcCost(it, dirDict) }
        seq1 = seq1.filter { calcCost(it, dirDict) == minL1 }

        var prevSeq = seq1; var seq2: List<String> = listOf()
        (0..<numRobot).forEach { i ->
            seq2 = if (i == 0) {
                prevSeq.flatMap{ seq -> findSeq(seq, dirDict, dirMap) }
            } else findSeq(prevSeq[0], dirDict, dirMap)
            val minL2 = seq2.minOfOrNull { calcCost(it, dirDict) }
            seq2 = seq2.filter { calcCost(it, dirDict) == minL2 }
            prevSeq = seq2
        }

        // val ins = seq2.flatMap{ seq -> findSeq(seq, dirDict, dirMap) }
        println(seq2[0])
        val ins = findSeq(seq2[0], dirDict, dirMap)
        val minL3 = ins.minOfOrNull { it.length }!!
        println("code: $code, com: $minL3, cn: $cn, tot: ${cn * minL3}")
        cn * minL3
    }
}

private fun calcCost(p1: Pair<Int, Int>, p2: Pair<Int, Int>): Int {
    val (x1, y1) = p1; val (x2, y2) = p2
    return abs(x1-x2) + abs(y1-y2)
}

private fun calcCost(str: String, dict: Map<Char, Pair<Int, Int>>): Int {
    if (str.length == 0) return -1
    else if (str.length == 1) {
        val (x1, y1) = dict['A']!!; val (x2, y2) = dict[str[0]]!!
        return 2 * (abs(x1-x2) + abs(y1-y2))
    } else {
        var p1 = dict['A']!!; var p2 = dict[str[0]]!!
        var ans = calcCost(p1, p2)
        p2 = dict[str[str.length-1]]!!
        ans += calcCost(p1, p2)
        p1 = dict[str[0]]!!; var pc = str[0]
        val sum = (1..<str.length).sumOf { i ->
            if (str[i] == pc) 0
            else {
                val diff = calcCost(p1, dict[str[i]]!!)
                p1 = dict[str[i]]!!; pc = str[i]
                diff
            }
        }
        return sum + ans
    }
}

private fun findSeq(
    code: String, dict: Map<Char, Pair<Int, Int>>, map: List<List<Char>>
): List<String> {
    val dirDict = mapOf(
        Pair('^', Pair(1,0)), Pair('A', Pair(2,0)),
        Pair('<', Pair(0,1)), Pair('v', Pair(1,1)), Pair('>', Pair(2,1))
    )
    var prev = 'A'; var ans = ""; var all = listOf("")
    code.forEachIndexed { i, c ->
        var ins = findShortest(dict[prev]!!, dict[c]!!, map)
        val minCost = ins.minOfOrNull { calcCost(it, dirDict) }
        ins = ins.filter { calcCost(it, dirDict) == minCost }
        all = all.flatMap { a -> when (i) {
            0 -> ins.map { b -> a + b }
            code.length - 1 -> ins.map { b -> a + "A" +  b + "A" }
            else -> ins.map { b -> a + "A" + b }
        } }
        ans += ins[0] + "A"; prev = c
    }
    return all
}

private val found: MutableMap< Pair<Pair<Int, Int>, Pair<Int, Int>>, List<String>> = mutableMapOf()
private val dirs = listOf(
    Triple(0,-1, '^'), Triple(1,0, '>'),
    Triple(0,1, 'v'), Triple(-1,0, '<'))
private fun findShortest(s: Pair<Int, Int>, e: Pair<Int, Int>, map: List<List<Char>>): List<String> {
    if (Pair(s, e) in found) return found[Pair(s, e)]!!

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
    found[Pair(s, e)] = ans
    return ans
}