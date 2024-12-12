import kotlin.io.path.Path
import kotlin.io.path.readLines

data class Edge(val x: Int, val y: Int, val dir: Int) // 1: up, 2: down, 3. left, 4: right
val dirs = listOf(
    Triple(1,0, 4), Triple(-1, 0, 3),
    Triple(0,1, 2), Triple(0, -1, 1))
fun main(args: Array<String>) {
    val map: List<List<Char>> = Path(args[0]).readLines().map { it.toList() }
    val w = map[0].size; val h = map.size
    val seen: MutableSet<Pair<Int, Int>> = mutableSetOf()
    val groups: MutableList<Pair<Int, List<Edge>>> = mutableListOf()

    val ans1 = (0..<w).sumOf{x -> (0..<h).sumOf{ y ->
        if (Pair(x,y) in seen) 0
        else {
            val group = findGroup(x, y, map[y][x], map, seen)
            val edges: MutableList<Edge> = mutableListOf()
            val size = group.size
            val perimeter = group.sumOf { (x,y) ->
                var ans = 4
                dirs.forEach{ (dx, dy, t) ->
                    if (Pair(x+dx,y+dy) in group) ans -= 1
                    else edges += Edge(x,y,t)
                }
                ans
            }
            groups += Pair(size, edges)
            size * perimeter
        }
    }}

    val ans2 = groups.sumOf { (size, edges) ->
        var copy = edges.toMutableList()
        var numEdges = 0
        while (copy.isNotEmpty()) {
            val (x,y,t) = copy[0]
            val curr = mutableListOf(copy.removeFirst())
            if (t == 1 || t == 2) {
                curr += copy.filter { (_, y2, t2) ->  y == y2 && t == t2}
                curr.sortBy { (x1,_,_) -> x1 }
                var prev = curr[0].x
                curr.forEach {
                    if(it.x - prev != 1) numEdges += 1
                    prev = it.x
                }
            }
            else {
                curr += copy.filter { (x2, _, t2) ->  x == x2 && t == t2}
                curr.sortBy { (_,y1,_) -> y1 }
                var prev = curr[0].y
                curr.forEach {
                    if(it.y - prev != 1) numEdges += 1
                    prev = it.y
                }
            }
            copy = copy.filter { it !in curr }.toMutableList()
        }
        size * numEdges
    }

    println("Ans 1: $ans1")
    println("Ans 2: $ans2")
}

fun findGroup(
    x: Int, y: Int, c: Char, map: List<List<Char>>, seen: MutableSet<Pair<Int, Int>>
): MutableList<Pair<Int, Int>> {
    val queue = ArrayDeque(listOf(Pair(x,y)))
    val found: MutableList<Pair<Int, Int>> = mutableListOf()
    while (queue.size > 0) {
        val (cx, cy) = queue.removeFirst()
        if (cx !in  0..<map[0].size || cy !in map.indices) continue
        if (map[cy][cx] != c || Pair(cx,cy) in seen) continue
        found += Pair(cx, cy); seen += Pair(cx, cy)
        dirs.forEach{(dx, dy) -> queue.addLast(Pair(cx+dx,cy+dy))}
    }
    return found
}