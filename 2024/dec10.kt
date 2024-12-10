import kotlin.io.path.Path
import kotlin.io.path.readLines

fun main(args: Array<String>) {
    val map: List<List<Int>> = Path(args[0]).readLines()
        .map { it.toList().map { c -> c.digitToInt() } }
    val w = map[0].size; val h = map.size
    val zeroPos = (0..<w).flatMap{ x -> (0..<h).flatMap{y ->
        if (map[y][x] == 0) listOf(Pair(x,y)) else listOf()
    } }

    val ans1 = zeroPos.sumOf {
        find9Pos(it.first, it.second, map, w, h, 1)
    }
    val ans2 = zeroPos.sumOf {
        find9Pos(it.first, it.second, map, w, h, 2)
    }

    println("Ans 1: $ans1")
    println("Ans 2: $ans2")
}

data class HPos(val x: Int, val y: Int, val h: Int)
fun find9Pos(
    sx: Int, sy: Int, map: List<List<Int>>, nc: Int, nr: Int, part: Int
): Int {
    val queue = ArrayDeque(listOf(HPos(sx,sy,-1)))
    val seen: MutableSet<Pair<Int, Int>> = mutableSetOf()
    var ans = 0

    while (!queue.isEmpty()) {
        val (x, y, h) = queue.removeFirst()
        if (x !in 0..<nc || y !in 0..<nr) continue
        if (map[y][x] - h != 1) continue
        if (part == 1 && Pair(x,y) in seen) continue
        if (map[y][x] == 9) ans += 1
        seen += Pair(x,y)
        listOf(Pair(x+1,y), Pair(x-1,y), Pair(x,y+1), Pair(x,y-1)).forEach{(a,b) ->
            queue.addLast(HPos(a, b,h+1))
        }
    }
    return ans
}