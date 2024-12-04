import kotlin.io.path.Path
import kotlin.io.path.readLines

fun main(args: Array<String>) {
    val lines: List<String> = Path(args[0]).readLines()
    val m = lines.map { it.toCharArray().toList() }
    val h = m.size; val w = m[0].size
    var ans1 = 0; var ans2 = 0

    (0..<w).forEach { x ->
        (0..<h).forEach{ y ->
            if (y+3 < h) {
                if (getString(x, y, 0, 1, m) == "XMAS") ans1 += 1
                if (getString(x, y, 0, 1, m) == "XMAS".reversed()) ans1 += 1
            }
            if (x+3 < w) {
                if (getString(x, y, 1, 0, m) == "XMAS") ans1 += 1
                if (getString(x, y, 1, 0, m) == "XMAS".reversed()) ans1 += 1
            }
            if (y+3 < h && x+3 < w) {
                if (getString(x, y, 1, 1, m) == "XMAS") ans1 += 1
                if (getString(x,y,1,1, m) == "XMAS".reversed()) ans1 += 1
            }
            if (y-3 >= 0 && x+3<w) {
                if (getString(x, y, 1, -1, m) == "XMAS") ans1 += 1
                if (getString(x,y,1,-1, m) == "XMAS".reversed()) ans1 += 1
            }

            if (x<w-2 && y<h-2) {
                val seq1 = listOf(m[y][x], m[y+1][x+1], m[y+2][x+2]).joinToString("")
                val seq2 = listOf(m[y+2][x], m[y+1][x+1], m[y][x+2]).joinToString("")
                if (seq1 == "MAS" || seq1.reversed() == "MAS") {
                    if (seq2 == "MAS" || seq2.reversed() == "MAS") ans2 += 1
                }
            }
        }
    }

    println("Ans 1: $ans1")
    println("Ans 2: $ans2")
}

fun getString(x: Int, y: Int, dx: Int, dy: Int, m: List<List<Char>>): String {
    return listOf(m[y][x], m[y + dy][x + dx], m[y + 2*dy][x + 2*dx], m[y + 3*dy][x + 3*dx]).joinToString("")
}