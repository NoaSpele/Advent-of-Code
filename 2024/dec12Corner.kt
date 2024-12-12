import kotlin.io.path.Path
import kotlin.io.path.readLines

private val dirs = listOf(Pair(1,0), Pair(-1, 0), Pair(0,1), Pair(0, -1))
fun main(args: Array<String>) {
    val map: List<List<Char>> = Path(args[0]).readLines().map { it.toList() }
    val w = map[0].size; val h = map.size
    val seen: MutableSet<Pair<Int, Int>> = mutableSetOf()

    var ans1 = 0L; var ans2 = 0L
    (0..<w).forEach{x -> (0..<h).forEach{ y ->
        if (Pair(x,y) !in seen) {
            val group = findGroup(x, y, map[y][x], map, seen)
            val size = group.size; var numCorners = 0
            val perimeter = group.sumOf { (x,y) ->
                if (Pair(x+1,y) in group && Pair(x,y+1) in group && Pair(x+1,y+1) !in group) numCorners += 1
                if (Pair(x+1,y) in group && Pair(x,y-1) in group && Pair(x+1,y-1) !in group) numCorners += 1
                if (Pair(x-1,y) in group && Pair(x,y+1) in group && Pair(x-1,y+1) !in group) numCorners += 1
                if (Pair(x-1,y) in group && Pair(x,y-1) in group && Pair(x-1,y-1) !in group) numCorners += 1

                if (Pair(x+1,y) !in group && Pair(x,y+1) !in group) numCorners += 1
                if (Pair(x+1,y) !in group && Pair(x,y-1) !in group) numCorners += 1
                if (Pair(x-1,y) !in group && Pair(x,y+1) !in group) numCorners += 1
                if (Pair(x-1,y) !in group && Pair(x,y-1) !in group) numCorners += 1

                dirs.sumOf{ (dx, dy) ->
                    if (Pair(x+dx,y+dy) !in group) 1 else 0L
                }
            }
            ans1 += size.toLong() * perimeter
            ans2 += size * numCorners
        }
    }}

    println("Ans 1: $ans1")
    println("Ans 2: $ans2")
}

private fun findGroup(
    x: Int, y: Int, c: Char, map: List<List<Char>>, seen: MutableSet<Pair<Int, Int>>
): MutableSet<Pair<Int, Int>> {
    val queue = ArrayDeque(listOf(Pair(x,y)))
    val found: MutableSet<Pair<Int, Int>> = mutableSetOf()
    while (queue.size > 0) {
        val (cx, cy) = queue.removeFirst()
        if (cx !in  0..<map[0].size || cy !in map.indices) continue
        if (map[cy][cx] != c || Pair(cx,cy) in seen) continue
        found += Pair(cx, cy); seen += Pair(cx, cy)
        dirs.forEach{(dx, dy) -> queue.addLast(Pair(cx+dx,cy+dy))}
    }
    return found
}