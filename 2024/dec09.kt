import kotlin.io.path.Path
import kotlin.io.path.readLines

fun main(args: Array<String>) {
    val diskMap: String = Path(args[0]).readLines()[0]
    val fileSystem: MutableList<Long> = mutableListOf()
    val files: MutableList<Int> = mutableListOf()
    var isFile = true; var fileId = 0L
    diskMap.map { it.digitToInt() }.forEach { num ->
        if (isFile) {
            files += num
            (0..<num).forEach { _ -> fileSystem += fileId }
            fileId += 1
        } else (0..<num).forEach { _ -> fileSystem += -1 }
        isFile = !isFile
    }

    val orgFileSys = fileSystem.toMutableList()
    println("Ans 1: ${solveP1(fileSystem)}")
    println("Ans 2: ${solveP2(files, orgFileSys)}")
}

fun solveP1(fileSystem: MutableList<Long>): Long {
    var numIdx: Int; var emptyIdx: Int
    while (true) {
        emptyIdx = fileSystem.indexOfFirst { it == -1L }
        numIdx = fileSystem.indexOfLast { it != -1L }
        if (emptyIdx > numIdx) break
        fileSystem[emptyIdx] = fileSystem[numIdx]
        fileSystem[numIdx] = -1L
    }
    return fileSystem.subList(0, emptyIdx+1).indices.sumOf {
        if (fileSystem[it] == -1L) 0
        else fileSystem[it] * it
    }
}

fun solveP2(files: List<Int>, fileSystem: MutableList<Long>): Long {
    files.indices.reversed().map { idx ->
        val currIdx = fileSystem.indexOfFirst { it == idx.toLong() }
        val newIdx = (0..<currIdx).find {
            fileSystem[it] == -1L && fileSystem.subList(it, it + files[idx]).toSet().size == 1
        }
        if (newIdx != null && newIdx < currIdx) {
            (0..<files[idx]).forEach {
                fileSystem[newIdx + it] = idx.toLong()
                fileSystem[currIdx + it] = -1
            }
        }
    }
    return fileSystem.indices.sumOf {
        if (fileSystem[it] == -1L) 0
        else fileSystem[it] * it
    }
}