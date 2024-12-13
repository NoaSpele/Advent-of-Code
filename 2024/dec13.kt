import kotlin.io.path.Path
import kotlin.io.path.readText
import kotlin.math.roundToLong

fun main(args: Array<String>) {
    val machines: List<String> = Path(args[0]).readText().split("\n\n")
    val btnRegex = Regex("Button .+: X\\+(\\d+), Y\\+(\\d+)")
    val prizeRegex = Regex("Prize: X=(\\d+), Y=(\\d+)")

    var ans1 = 0L; var ans2 = 0L
    machines.forEach { machine ->
        val ins = machine.split("\n")
        val valuesA = btnRegex.find(ins[0])?.groupValues
        val ax = valuesA?.get(1)!!.toDouble(); val ay = valuesA[2].toDouble()
        val valuesB = btnRegex.find(ins[1])?.groupValues
        val bx = valuesB?.get(1)!!.toDouble(); val by = valuesB[2].toDouble()
        val valuesE = prizeRegex.find(ins[2])?.groupValues
        val ex = valuesE?.get(1)!!.toDouble(); val ey =  valuesE[2].toDouble()
        val ex2 = ex + 10000000000000; val ey2 = ey + 10000000000000

        val b = (ey - (ex/ax)*ay) / ((-bx*ay + by*ax)/ax)
        val a = (ex - b*bx)/ax

        val aLong = a.roundToLong(); val bLong = b.roundToLong()
        if (aLong in 0..100 && bLong in 0..100
            && aLong*ax + bLong*bx == ex && aLong*ay + bLong*by == ey) {
            ans1 += aLong*3L + bLong
        }

        val b2 = (ey2 - (ex2/ax)*ay) / ((-bx*ay + by*ax)/ax)
        val a2 = (ex2 - b2*bx)/ax
        val aLong2 = a2.roundToLong(); val bLong2 = b2.roundToLong()
        if (aLong2*ax + bLong2*bx == ex2 && aLong2*ay + bLong2*by == ey2) ans2 += aLong2*3L + bLong2
    }

    println("Ans 1: $ans1")
    println("Ans 2: $ans2")
}