import kotlin.io.path.Path
import kotlin.io.path.readLines

fun main(args: Array<String>) {
    val lines: List<String> = Path(args[0]).readLines()
    val adj: MutableMap<String, List<String>> = mutableMapOf()
    lines.forEach { line ->
        val (a,b) = line.split("-")
        if (a !in adj) adj += Pair(a, listOf())
        if (b !in adj) adj += Pair(b, listOf())
        adj[a] = adj[a]!! + listOf(b)
        adj[b] = adj[b]!! + listOf(a)
    }

    val con3: MutableList<Set<String>> = mutableListOf()
    val ans1 = adj.keys.filter { k -> k[0] == 't' }.sumOf { a ->
        adj.keys.sumOf { b ->
            adj.keys.sumOf { c ->
                if (
                    b in adj[a]!! && c in adj[a]!! &&
                    a in adj[b]!! && c in adj[b]!! &&
                    a in adj[c]!! && b in adj[c]!! &&
                    setOf(a,b,c) !in con3
                ) { con3 += setOf(a,b,c); 1 } else 0L
            }
        }
    }
    val ans2 = 0

    println("Ans 1: $ans1")
    println("Ans 2: $ans2")
}