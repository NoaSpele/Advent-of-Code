import kotlin.io.path.Path
import kotlin.io.path.readText

fun main(args: Array<String>) {
    val nums: MutableList<Long> = Path(args[0]).readText().split(' ').map { it.toLong() }.toMutableList()
    var freqMap: MutableMap<Long, Long> = mutableMapOf()
    nums.forEach{ num ->
        if (num !in freqMap) freqMap[num] = 1
        else freqMap[num] = freqMap[num]!! + 1
    }

    var ans1: Long = 0
    (0..<75).forEach { idx ->
        val newFreqMap: MutableMap<Long, Long> = mutableMapOf()
        if (idx == 25) ans1 = freqMap.map {(_,f) -> f }.sum()
        freqMap.forEach {(num, freq) ->
            val next: List<Long>
            if (num == 0L) next = listOf(1)
            else if (num.toString().length  % 2 == 0) {
                val halfIdx = num.toString().length / 2
                next = listOf(
                    num.toString().substring(0, halfIdx).toLong(),
                    num.toString().substring(halfIdx, halfIdx*2).toLong()
                )
            } else next = listOf(num * 2024)
            next.forEach { n ->
                if (n !in newFreqMap) newFreqMap[n] = 0
                newFreqMap[n] = newFreqMap[n]!! + freq
            }
        }
        freqMap = newFreqMap
    }

    println("Ans 1: $ans1")
    println("Ans 2: ${freqMap.map {(_,f) -> f }.sum()}")
}