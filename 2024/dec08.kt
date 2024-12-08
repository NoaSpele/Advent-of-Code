import kotlin.io.path.Path
import kotlin.io.path.readLines

data class Antenna (val freq: Char, val x: Int, val y: Int)

fun main(args: Array<String>) {
    val map: List<List<Char>> = Path(args[0]).readLines().map { it.toList() }
    val antennas: MutableList<Antenna> = mutableListOf()
    for (x in map[0].indices) {
        for (y in map.indices) {
            if (map[y][x].isLetterOrDigit()) antennas += Antenna(map[y][x], x, y)
        }
    }
    println("Ans 1: ${findAllPos(antennas, map[0].size, map.size, 1).size}")
    println("Ans 2: ${findAllPos(antennas, map[0].size, map.size, 2).size}")
}

fun findAllPos(antennas: List<Antenna>, w: Int, h: Int, part: Int): Set<Pair<Int, Int>> {
    var pos: Set<Pair<Int, Int>> = setOf()
    for (i in antennas.indices) {
        antennas.subList(i+1,antennas.size).filter { it.freq == antennas[i].freq }
            .forEach { antenna ->
                val dx = antennas[i].x - antenna.x; val dy = antennas[i].y - antenna.y
                if (part == 1) {
                    pos = addPos(antennas[i].x + dx, antennas[i].y + dy, w, h, pos)
                    pos = addPos(antenna.x - dx, antenna.y - dy, w, h, pos)
                } else {
                    var tx = antennas[i].x; var ty = antennas[i].y
                    while (tx in 0..<w && ty in 0..<h) {
                        pos = addPos(tx, ty, w, h, pos)
                        tx += dx; ty += dy
                    }
                    tx = antenna.x; ty = antenna.y
                    while (tx in 0..<w && ty in 0..<h) {
                        pos = addPos(tx, ty, w, h, pos)
                        tx -= dx; ty -= dy
                    }
                }
            }
    }
    return pos
}

fun addPos (x: Int, y: Int, w: Int, h: Int, pos: Set<Pair<Int, Int>>): Set<Pair<Int, Int>> {
    if (x in 0..<w && y in 0..<h && Pair(x, y) !in pos) {
        return pos + Pair(x, y)
    } else return pos
}