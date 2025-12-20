import kotlin.io.path.Path
import kotlin.io.path.readLines

val input = Path(args[0]).readLines()[0]
val inter = input.split(",").map {
    it.split("-")[0].toLong()..it.split("-")[1].toLong()
}
var ans1 = 0L; var ans2 = 0L
inter.forEach { it.forEach { id ->
    val idStr = id.toString()
    if (idStr.length % 2 == 0) {
        if (idStr.take(idStr.length/2) == idStr.substring(idStr.length/2, idStr.length)) ans1 += id
    }
    for (c in (1..idStr.length/2)) {
        if (idStr.length % c == 0) {
            if (idStr.take(c).repeat(idStr.length/c) == idStr) {
                ans2 += id; break
            }
        }
    }
} }
println("ans1: $ans1, ans2: $ans2")