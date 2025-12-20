import kotlin.io.path.Path
import kotlin.io.path.readLines

val input = Path(args[0]).readLines()
var angle = 50
var ans1 = 0; var ans2 = 0

input.forEach {
    val dir = it.first(); val num = it.drop(1).toInt()
    if (angle == 0 && dir == 'L') ans2--
    if (dir == 'R') angle += num else angle -= num
    while (angle > 99) {
        angle -= 100
        if (angle != 0) ans2++
    }
    while (angle < 0) {
        angle += 100; ans2++
    }
    if (angle == 0) {
        ans1++; ans2++
    }
}
println("ans1: $ans1, ans2: $ans2")