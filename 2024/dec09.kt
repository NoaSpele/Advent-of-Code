import kotlin.io.path.Path
import kotlin.io.path.readLines

fun main(args: Array<String>) {
    val diskMap: String = Path(args[0]).readLines()[0]
    val fileSystem: MutableList<Long> = mutableListOf()
    val files: MutableList<Pair<Int, Int>> = mutableListOf()
    val spaces: MutableList<Pair<Int, Int>> = mutableListOf()
    var isFile = true; var fileId = 0L
    diskMap.map { it.digitToInt() }.forEach { num ->
        if (isFile) {
            files += Pair(fileSystem.size, num)
            (0..<num).forEach { _ -> fileSystem += fileId }
            fileId += 1
        } else {
            spaces += Pair(fileSystem.size, num)
            (0..<num).forEach { _ -> fileSystem += -1 }
        }
        isFile = !isFile
    }
    println("Ans 1: ${solveP1(fileSystem.toMutableList())}")
    println("Ans 2: ${solveP2(files, spaces, fileSystem)}")
}

fun solveP1(fileSystem: MutableList<Long>): Long {
    var numIdx = fileSystem.size -1; var emptyIdx = 0
    while (true) {
        emptyIdx = (emptyIdx..<fileSystem.size).first { fileSystem[it] == -1L }
        numIdx = (0..numIdx).reversed().first { fileSystem[it] != -1L }
        if (emptyIdx > numIdx) break
        fileSystem[emptyIdx] = fileSystem[numIdx]
        fileSystem[numIdx] = -1L
    }
    return fileSystem.subList(0, emptyIdx).indices.sumOf { fileSystem[it] * it }
}

fun solveP2(
    files: List<Pair<Int, Int>>, spaces: MutableList<Pair<Int, Int>>,
    fileSystem: MutableList<Long>
): Long {
    files.indices.reversed().map { idx ->
        val spaceIdx = spaces.indexOfFirst { it.second >= files[idx].second }
        if (spaceIdx != -1 && spaces[spaceIdx].first < files[idx].first) {
            val curr = spaces[spaceIdx]
            (0..<files[idx].second).forEach {
                fileSystem[curr.first + it] = idx.toLong()
                fileSystem[files[idx].first + it] = -1
            }
            spaces[spaceIdx] = Pair(curr.first + files[idx].second, curr.second - files[idx].second)
            if (spaces[spaceIdx].second == 0) spaces.removeAt(spaceIdx)
        }
    }
    return fileSystem.indices.sumOf {
        if (fileSystem[it] == -1L) 0 else fileSystem[it] * it
    }
}