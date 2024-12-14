import kotlin.io.path.Path
import kotlin.io.path.readLines

data class Robot (var x: Int, var y: Int, val dx: Int, val dy: Int)
fun main(args: Array<String>) {
    val lines: List<String> = Path(args[0]).readLines()
    val robots: MutableList<Robot> = mutableListOf()
    val w = 101; val h = 103
    // val w = 11; val h = 7

    lines.forEach { line ->
        val (p1, p2) = line.split(' ')
        val (x,y) = p1.split('=')[1].split(',').map { it.toInt() }
        val (dx,dy) = p2.split('=')[1].split(',').map { it.toInt() }
        robots += Robot(x,y,dx,dy)
    }

    var ans1 = 0L; val ans2: Int; var i = 1
    while (true) {
        robots.forEach { robot ->
            robot.x = (robot.x + robot.dx) % w
            if (robot.x < 0) robot.x += w
            robot.y = (robot.y + robot.dy) % h
            if (robot.y < 0) robot.y += h
        }
        val (q1,q2,q3,q4) = getQuadrants(robots, w, h, w/2, h/2)
        if (i == 100) ans1 = q1 * q2 * q3 * q4

        // Assumes that the image happens when all positions are unique
        val poses = robots.map { Pair(it.x, it.y) }.toSet()
        if (poses.size == robots.size) {
            ans2 = i; drawBoard(robots, i, w, h); break
        }
        i += 1
    }

    println("Ans 1: $ans1")
    println("Ans 2: $ans2")
}

fun getQuadrants(robots: List<Robot>, w: Int, h: Int, c: Int, r: Int): List<Long> {
    val hw = c; val hh = r
    val q1 = robots.sumOf { if (it.x in 0..<hw && it.y in 0..<hh) 1 else 0L }
    val q3 = robots.sumOf { if (it.x in 0..<hw && it.y in hh+1..<h) 1 else 0L }
    val q2 = robots.sumOf { if (it.x in hw+1..<w && it.y in 0..<hh) 1 else 0L }
    val q4 = robots.sumOf { if (it.x in hw+1..<w && it.y in hh+1..<h) 1 else 0L }
    return listOf(q1, q2, q3, q4)
}

fun drawBoard(robots: List<Robot>, i: Int, w: Int, h: Int) {
    println("==== $i ====\n")
    (0..<h).forEach{y ->
        (0..<w).forEach { x ->
            val found = robots.find { it.x == x && it.y == y }
            if (found != null) print('#') else print('.')
        }
        println()
    }
    println()
}