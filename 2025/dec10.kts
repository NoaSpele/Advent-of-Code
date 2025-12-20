import kotlin.io.path.Path
import kotlin.io.path.readLines

data class Machine(val active: List<Boolean>, val buttons: List<List<Int>>, val jolt: List<Int>)
val input = Path(args[0]).readLines().map { str ->
    val parts = str.split(" ")
    val active = parts[0].substring(1,parts[0].length-1).map { it =='#' }
    val buttons = parts.subList(1, parts.size-1)
        .map { it.substring(1, it.length-1) }
        .map { it.split(",").map { s -> s.toInt() } }
    val jolt = parts.last().substring(1, parts.last().length-1).split(",").map { it.toInt() }
    Machine(active, buttons, jolt)
}

fun findAll(btns: List<List<Int>>, active: List<Boolean>): List<Int> {
    return (0..<(1 shl btns.size)).filter {
        val state = active.map { false }.toMutableList()
        btns.withIndex().forEach { b ->
            if (it and (1 shl b.index) != 0) b.value.forEach { x -> state[x] = !state[x] }
        }
        state == active
    }
}
val ans1 = input.sumOf { findAll(it.buttons, it.active).minOf { mask -> Integer.bitCount(mask) } }
println("ans1: $ans1")

var seen = mutableMapOf<List<Int>, Int>()
fun findMin(btns: List<List<Int>>, values: List<Int>): Int {
    if (values.any { it < 0 }) return 10000
    if (values.all { it == 0 }) return 0
    seen[values]?.let { return it }
    val possible = findAll(btns, values.map { it % 2 == 1 })
    if (possible.isEmpty()) return 10000
    seen[values] = possible.minOf {
        val activeIdx = (0..<btns.size).filter { i -> it and (1 shl i) != 0 }
        val copy = values.toMutableList()
        activeIdx.map { i -> btns[i] }.forEach { b -> b.forEach { i-> copy[i] -= 1 } }
        2*findMin(btns, copy.map { v -> v shr 1 }) + activeIdx.size
    }
    return seen[values]!!
}

val ans2 = input.sumOf { seen = mutableMapOf(); findMin(it.buttons, it.jolt) }
println("ans2: $ans2")