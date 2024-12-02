import kotlin.io.path.Path
import kotlin.io.path.readLines
import kotlin.math.*

// Extension of kotlin.io which is a standard lib
import java.io.File

/*
To run:
Option 1)
  1) kotlinc template.kt -include-runtime -d template.jar
  2) java -jar template.jar input.txt

Option 2)
  1) kotlinc template.kt -d template.jar
  2) kotlin -classpath template.jar TemplateKt input.txt
*/

fun main(args: Array<String>) {
    Path(args[0]).readLines().map { println(it) }

    File(args[0]).forEachLine {println(it)}

    println(round(sin(PI) * 1000.0)/1000.0)
}