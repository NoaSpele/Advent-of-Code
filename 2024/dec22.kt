import kotlin.io.path.Path
import kotlin.io.path.readLines

fun main(args: Array<String>) {
    val nums: List<Long> = Path(args[0]).readLines().map { it.toLong() }
    val prices: MutableList<MutableList<Long>> = mutableListOf()
    val diffs: MutableList<MutableList<Long>> = mutableListOf()
    val toCheck: MutableSet<List<Long>> = mutableSetOf()
    val first: MutableMap<List<Long>, Long> = mutableMapOf()
    var ans1 = 0L
    nums.forEachIndexed { i, num ->
        var next = num; var oldPrice = num % 10
        prices.addLast(mutableListOf())
        diffs.addLast(mutableListOf())
        (0..<2000).forEach { _->
            next = generateNext(next)
            val price = next % 10L
            prices[i].addLast(price)
            diffs[i].addLast(price - oldPrice)
            oldPrice = price
        }
        (0..diffs[i].size-4).forEach { j ->
            val currList = diffs[i].subList(j, j+4)
            val fl = listOf(i.toLong()) + currList
            // Assuming that at least one sequence has price 9 for optimal, works without assumption
            if (prices[i][j+3] == 9L && currList !in toCheck) toCheck += currList
            if (fl !in first) first += Pair(fl, prices[i][j+3])
        }
        ans1 += next
    }
    println("Ans 1: $ans1")

    var ans2 = 0L
    toCheck.forEachIndexed { ci, list ->
        if ((ci+1) % 2000 == 0) println("${ci+1}/${toCheck.size}, best: $ans2")
        val curr = nums.indices.sumOf { ni ->
            val cl = listOf(ni.toLong()) + list
            if (cl in first) first[cl]!! else 0
        }
        if (curr > ans2) ans2 = curr
    }
    println("Ans 2: $ans2")
}

private fun generateNext(num: Long): Long {
    var next = num
    next = next*64 xor next; next %= 16777216
    next = next / 32L xor next; next %= 16777216
    next = next*2048 xor next; return next % 16777216
}