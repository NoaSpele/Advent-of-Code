import kotlin.io.path.Path
import kotlin.io.path.readLines

private val dirs = listOf(
    Triple(1,0, 0),
    Triple(-1,0, 1),
    Triple(0,1, 2),
    Triple(0,-1, 3))
data class Data (val x: Int, val y: Int, val dir: Int, val c: Int, val p: String)
fun main(args: Array<String>) {
    val map: List<List<Char>> = Path(args[0]).readLines().map { it.toList() }
    val w = map[0].size; val h = map.size
    var sPos = Pair(-1, -1); var ePos = Pair(-1, -1)
    for (x in 0..<w) { for(y in 0..<h) {
        if (map[y][x] == 'S') sPos = Pair(x,y)
        if (map[y][x] == 'E') ePos = Pair(x,y)
    } }

    val ans1 = findCheapest(sPos, ePos, map, null).first
    println("Ans 1: $ans1")

    val bestPaths = findCheapest(sPos, ePos, map, ans1).second
    val ans2 = bestPaths.flatMap { it.substring(1,it.length).split(";").map { str ->
        Pair(str.split(",")[0].toInt(), str.split(",")[1].toInt())
    } }.toSet().size
    println("Ans 2: $ans2")
}

fun findCheapest(
    sPos: Pair<Int, Int>, ePos: Pair<Int, Int>, map: List<List<Char>>, best: Int?
): Pair<Int?, List<String>> {
    val w = map[0].size; val h = map.size
    val paths: MutableList<String> = mutableListOf()
    val queue = mutableListOf(Data(
        sPos.first, sPos.second, 2, 0, ""
    ))
    val seen: MutableMap<Triple<Int, Int, Int>, Int> = mutableMapOf()
    while (queue.isNotEmpty()) {
        val (x,y,dir,c,p) = queue.removeFirst()
        if (x !in 0..<w || y !in 0..<h) continue
        if (map[y][x] == '#') continue
        val currSeen = seen[Triple(x,y,dir)]
        if (currSeen != null && currSeen < c) continue
        seen[Triple(x,y,dir)] = c
        if (x == ePos.first && y == ePos.second) {
            if (best != null && best == c) paths += "$p;$x,$y"
            continue
        }
        dirs.forEach { (dx, dy, d) ->
            val toAdd = if (d == dir) 1 else 1001
            queue.addLast(Data(x+dx, y+dy, d, c + toAdd, "$p;$x,$y"))
        }
    }
    val shortest = (0..4).minOf {
        seen[Triple(ePos.first, ePos.second, it)] ?: Integer.MAX_VALUE
    }
    return Pair(shortest, paths)
}