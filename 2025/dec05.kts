import kotlin.io.path.Path
import kotlin.io.path.readText

val (p1, p2) = Path(args[0]).readText().split("\n\n")
val ranges = p1.split("\n").map { it.split("-")[0].toLong()..it.split("-")[1].toLong() }
val ids = p2.split("\n").map { it.toLong() }
val ans1 = ids.filter { ranges.any { r -> it in r } }.size

fun canCombine(r1: LongRange, r2: LongRange): Boolean {
    return r1.first <= r2.last && r2.first <= r1.last
}

var newRanges = listOf(ranges[0])
ranges.drop(1).forEach { r ->
    val comb = newRanges.filter { canCombine(r, it) }
    var newS = r.first; var newE = r.last
    comb.forEach {
        if (it.first < newS) newS = it.first
        if (it.last > newE) newE = it.last
    }
    val keep = newRanges.filter { !canCombine(r, it) }
    newRanges = keep + listOf(LongRange(newS, newE))
}
val ans2 = newRanges.sumOf { it.last - it.first + 1 }

println("$ans1 $ans2")