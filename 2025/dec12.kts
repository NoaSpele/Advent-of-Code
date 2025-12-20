import kotlin.io.path.Path
import kotlin.io.path.readText

val input = Path(args[0]).readText().split("\n\n")
val cases = input.takeLast(1)[0].split("\n").map {
    val p1 = it.split(": ")
    val w = p1[0].split("x")[0].toInt(); val h = p1[0].split("x")[1].toInt()
    val nums = p1[1].split(" ").map { str -> str.toInt() }
    Triple(w,h, nums)
}
val shapes = input.dropLast(1).map {
    val map = it.split("\n").drop(1).map { l -> l.map { c -> if (c == '#') 1 else 0 } }
    val size = map.sumOf { r -> r.sum() }
    Pair(size, map)
}

var numS = 0
cases.forEach { (w,h,nums) ->
    val shapeSum = nums.withIndex().sumOf { (i, v) -> shapes[i].first * v }
    if (w*h < shapeSum) numS += 1
}
println("ans: ${cases.size - numS}")