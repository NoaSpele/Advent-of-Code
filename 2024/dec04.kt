import kotlin.io.path.Path
import kotlin.io.path.readLines

fun main(args: Array<String>) {
    val lines: List<String> = Path(args[0]).readLines()
    val m = lines.map { it.toCharArray().toList() }
    val h = m.size; val w = m[0].size

    var ans1 = 0
    lines.forEach { ans1 += getNumXmas(it) }
    (0..<w).forEach { ans1 += getNumXmas(m.map {row -> row[it] }.joinToString("")) }

    for (i in 0..<h-3) { ans1 += getNumXmas(getDiagonalSeq(i, 0, m)) }
    for (i in 1..<w-3) { ans1 += getNumXmas(getDiagonalSeq(0, i, m)) }

    val flipped = m.map { it.reversed() }
    for (i in 0..<h-3) { ans1 += getNumXmas(getDiagonalSeq(i, 0, flipped)) }
    for (i in 1..<w-3) { ans1 += getNumXmas(getDiagonalSeq(0, i, flipped)) }

    var ans2 = 0
    for (x in 0..<w-2) {
        for (y in 0 ..<h-2) {
            val seq1 = charArrayOf(m[x][y], m[x+1][y+1], m[x+2][y+2]).joinToString("")
            val seq2 = charArrayOf(m[x+2][y], m[x+1][y+1], m[x][y+2]).joinToString("")
            if (
                (seq1 == "MAS" || seq1.reversed() == "MAS")
                && (seq2 == "MAS" || seq2.reversed() == "MAS")
            ) ans2 += 1
        }
    }

    println("Ans 1: $ans1")
    println("Ans 2: $ans2")
}

fun getNumXmas (seq: String): Int {
    val re = Regex("XMAS")
    return re.findAll(seq).toList().size + re.findAll(seq.reversed()).toList().size
}

fun getDiagonalSeq(sr: Int, sc: Int, m: List<List<Char>>): String {
    val seq: MutableList<Char> = mutableListOf()
    var cx = sc; var cy = sr
    while (cx < m[0].size && cy < m.size) {
        seq += m[cy][cx]
        cx += 1; cy += 1
    }
    return seq.joinToString("")
}