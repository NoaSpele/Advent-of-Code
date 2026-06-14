import kotlin.io.path.Path
import kotlin.io.path.readText

private val dirs = listOf(Pair(0,-1), Pair(1,0), Pair(0,1), Pair(-1,0))
fun main(args: Array<String>) {
    val text: String = Path(args[0]).readText()
    val (mapStr, instructions) = text.split("\n\n")
    val dirList = instructions.replace("\n","").map { c ->
        when (c) { '^' -> 0; '>' -> 1; 'v' -> 2; else -> 3 }
    }
    val map = mapStr.split("\n").map { it.toMutableList() }
    val map2: List<MutableList<Char>> = mapStr.split('\n').map { mutableListOf() }
    val w = map[0].size; val h = map.size

    var rPos = Pair(-1,-1); var rPos2 = Pair(-1,-1)
    val boxes: MutableList<List<Pair<Int, Int>>> = mutableListOf()
    val boxes2: MutableList<List<Pair<Int, Int>>> = mutableListOf()
    for(y in 0..<h) { for (x in 0..<w) {
        if (map[y][x] == '@') {
            rPos = Pair(x,y); rPos2 = Pair(2*x,y)
            map2[y].addAll(listOf('.', '.'))
            map[y][x] = '.'
        }
        else if (map[y][x] == 'O') {
            boxes.addLast(listOf(Pair(x,y)))
            boxes2.addLast(listOf(Pair(2*x,y), Pair(2*x+1,y)))
            map2[y].addAll(listOf('.','.'))
            map[y][x] = '.'
        }
        else map2[y].addAll(listOf(map[y][x], map[y][x]))
    } }
    val w2 = map2[0].size; val h2 = map2.size

    dirList.indices.forEach { idx ->
        rPos = move(rPos, dirList[idx], map, boxes)
    }

    drawBoard(map2, rPos2, boxes2)
    // println(boxes2)
    dirList.indices.forEach { idx ->
        rPos2 = move(rPos2, dirList[idx], map2, boxes2)
        println("move: ${instructions[idx]}, x: ${rPos2.first}, y: ${rPos2.second}")
        drawBoard(map2, rPos2, boxes2)
        println()
    }

    println("x: ${rPos.first}, y: ${rPos.second}")
    drawBoard(map2, rPos2, boxes2)
    val ans1 = boxes.sumOf { it[0].first + 100*it[0].second }
    val ans2 = boxes2.sumOf { it[0].first + 100*it[0].second }

    println("Ans 1: $ans1")
    println("Ans 2: $ans2")
}

fun drawBoard(
    map: List<MutableList<Char>>, robot: Pair<Int, Int>,
    boxes: List<List<Pair<Int, Int>>>
) {
    for (y in map.indices) { for (x in 0..<map[0].size) {
        val box = boxes.find { it.contains(Pair(x,y)) }
        if (robot.first == x && robot.second == y) print('@')
        else if (box != null) print('O')
        else print(map[y][x])
    }; println() }
}

fun move(
    rPos: Pair<Int, Int>, dir: Int, map: List<MutableList<Char>>,
    boxes: MutableList<List<Pair<Int, Int>>>
): Pair<Int, Int> {
    val (dx, dy) = dirs[dir]
    val x = rPos.first + dx; val y = rPos.second + dy
    val boxAtPos = boxes.indexOfFirst { it.contains(Pair(x,y)) }
    if (boxAtPos != -1) {
        val (canMove, boxesToMove) = moveBoxes(mutableSetOf(Pair(x,y)), map, boxes)
        if (canMove) {
            boxesToMove.forEach { idx ->
                boxes[idx] = boxes[idx].map { Pair(it.first + dx, it.second + dy) }
            }
            return Pair(rPos.first + dx, rPos.second + dy)
        } else {
            return rPos
        }
    } else if (map[y][x] != '#') {
        return Pair(x,y)
    }
    return rPos
}

fun moveBoxes(
    next: MutableSet<Pair<Int, Int>>, map: List<MutableList<Char>>,
    boxes: MutableList<List<Pair<Int, Int>>>
): Pair<Boolean, Set<Int>> {
    val found: MutableSet<Int> = mutableSetOf()
    while (true) {
        var nextPoses = next.toMutableSet()
        var newPoses: MutableSet<Pair<Int, Int>> = mutableSetOf()
        nextPoses.forEach { (x,y) ->
            if (map[y][x] == '#') return Pair(false, setOf())
            val foundBax = boxes.find { it.contains(Pair(x, y)) }
            if (foundBax != null) {
                newPoses.addAll(foundBax)
            }
        }
        if (nextPoses.size == 0) return Pair(true, found)
        /* found.add(boxAtPos)
        x += dx; y += dy
        boxAtPos = boxes.indexOfFirst { it.contains(Pair(x, y)) }*/
    }
}