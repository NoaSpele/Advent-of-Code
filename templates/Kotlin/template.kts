import kotlin.io.path.Path
import kotlin.io.path.readLines
import kotlin.io.path.readText

// Run with kotlin template.kts in.txt

// If input is per line
val input = Path(args[0]).readLines()

// If input is two sections
val (p1, p2) = Path(args[0]).readText().split("\n\n")

// If input is a map
val map = Path(args[0]).readLines().map { it.map { c->c } }
val h = map.size; val w = map[0].size

(0..<h).sumOf { y -> (0..<w).sumOf { x ->
    x+y
} }

// Dirs in 2d
val dirs4 = listOf(Pair(0,1), Pair(0,-1), Pair(1,0), Pair(-1,0))
val dirs8 = listOf(
    Pair(0,1), Pair(0,-1), Pair(1,0), Pair(-1,0),
    Pair(1,1), Pair(1,-1), Pair(-1,1), Pair(-1,-1)
)

println("Hello AOC!")