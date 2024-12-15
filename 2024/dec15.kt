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
            // map[y][x] = '.'
        }
        else if (map[y][x] == 'O') {
            boxes.addLast(listOf(Pair(x,y)))
            boxes2.addLast(listOf(Pair(2*x,y), Pair(2*x+1,y)))
            map2[y].addAll(listOf('.','.'))
            // map[y][x] = '.'
        }
        else map2[y].addAll(listOf(map[y][x], map[y][x]))
    } }
    val w2 = map2[0].size; val h2 = map2.size

    drawBoard(map2, rPos2, boxes2)
    println(boxes2)
    dirList.indices.forEach { idx ->
        rPos = move(rPos, dirList[idx], map, boxes)
        // println("move: ${instructions[idx]}, x: ${rPos.first}, y: ${rPos.second}")
        // drawBoard(map, rPos)
    }

    println("x: ${rPos.first}, y: ${rPos.second}")
    drawBoard(map, rPos, boxes)
    println(boxes)
    val ans1 = (0..<w).sumOf { x -> (0..<h).sumOf { y ->
        if (map[y][x] == 'O') x + 100*y
        else 0
    } }
    val ans2 = 0

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
    boxes: List<List<Pair<Int, Int>>>
): Pair<Int, Int> {
    val (dx, dy) = dirs[dir]
    var x = rPos.first + dx; var y = rPos.second + dy
    if (map[y][x] == 'O') {
        var numBoxes = 1
        while (map[y + dy][x + dx] == 'O') {
            numBoxes += 1
            x += dx; y += dy
        }
        if (map[y+dy][x+dx] != '#') {
            (0..<numBoxes).forEach {
                map[rPos.second + dy*(2+it)][rPos.first + dx*(2+it)] = 'O'
            }
            map[rPos.second + dy][rPos.first + dx] = '.'
            return Pair(rPos.first + dx, rPos.second + dy)
        } else {
            return rPos
        }
    } else if (map[y][x] != '#') {
        return Pair(x,y)
    }
    return rPos
}