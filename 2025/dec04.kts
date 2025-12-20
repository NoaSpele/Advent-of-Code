import kotlin.io.path.Path
import kotlin.io.path.readLines

val dirs = listOf(
    Pair(0,1), Pair(0,-1), Pair(1,0), Pair(-1,0),
    Pair(1,1), Pair(1,-1), Pair(-1,1), Pair(-1,-1)
)

fun hasPaper(x: Int, y: Int): Int {
    if (x !in 0..<w || y !in 0..<h) return 0
    return if (map[y][x] == '@') 1 else 0
}

fun getAll(): List<Pair<Int, Int>> {
    return (0..<h).flatMap { y -> (0..<w).mapNotNull { x ->
        if (map[y][x] == '@' && dirs.sumOf { hasPaper(x + it.first, y + it.second) } < 4) {
            Pair(x,y)
        } else null
    } }
}

val map = Path(args[0]).readLines().map { it.map { c->c }.toMutableList() }
val h = map.size; val w = map[0].size

var curr = getAll(); var ans2 = 0; val ans1 = curr.size
while (curr.isNotEmpty()) {
    ans2 += curr.size
    curr.forEach { p -> map[p.second][p.first] = '.' }
    curr = getAll()
}

println("ans1: $ans1 ans2: $ans2")