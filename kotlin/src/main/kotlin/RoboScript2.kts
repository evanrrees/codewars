/** RS1 instruction */
enum class RS1 { F, R, L }

/** Command to execute the [rs1] instruction and specified number of [times] */
data class Command(val rs1: RS1, val times: Int = 1) {
    override fun toString(): String = "$rs1$times"
}

/** Orientation for RS1 [Pointer], given in [x] and [y] coordinates */
enum class Orientation(val x: Int, val y: Int) {

    N(0, 1), S(0, -1), E(1, 0), W(-1, 0);

    /** Process [command] and return an updated [Orientation] */
    fun update(command: Command): Orientation =
        when (command.rs1) {
            RS1.R -> turnR(command.times % 4)
            RS1.L -> turnL(command.times % 4)
            RS1.F -> this
        }

    /** Turn left [n] times and return a new [Orientation] */
    private fun turnL(n: Int): Orientation =
        if (n == 0) this
        else when (this) {
            N -> W
            W -> S
            S -> E
            E -> N
        }.turnL(n - 1)

    /** Turn right [n] times and return a new [Orientation] */
    private fun turnR(n: Int): Orientation =
        if (n == 0) this
        else when (this) {
            N -> E
            E -> S
            S -> W
            W -> N
        }.turnR(n - 1)

}

/** Store current state in [x] and [y] coordinates and an [orientation] */
data class Pointer(val x: Int = 0, val y: Int = 0, val orientation: Orientation = Orientation.E) {
    /** Process an RS1 [command] and return an updated [Pointer] */
    fun update(command: Command): Pointer {
        val o = orientation.update(command)
        var xNew = 0
        var yNew = 0
        if (command.rs1 == RS1.F) {
            xNew = o.x * command.times
            yNew = o.y * command.times
        }
        return Pointer(x + xNew, y + yNew, o)
    }
}

/** Holder for grid size as [n], [e], [s], [w]*/
class Grid(var n: Int = 0, var e: Int = 0, var s: Int = 0, var w: Int = 0) {

    override fun toString(): String = "(n: $n, s: $s, e: $e, w: $w)"

    companion object {
        /** Determine [Grid] size given a list of [commands]*/
        fun sketch(commands: List<Command>): Grid = Grid().apply {
            var pointer = Pointer()
            commands.forEach { cmd ->
                pointer = pointer.update(cmd)
                this.expand(pointer)
            }
//            System.err.println(this)
        }
    }

    /** Sketch and return a string representation of this [Grid] given a list of [commands] */
    fun draw(commands: List<Command>): String {
        var pointer = Pointer()
        val gridArray = Array(n - s + 1) { Array(e - w + 1) { " " } }
        gridArray[-s][-w] = "*"
        commands.forEach { cmd ->
//            System.err.println("$cmd, $pointer")
            val newPointer = pointer.update(cmd)
            if (cmd.rs1 == RS1.F) {
                val (xStart, xEnd) = listOf(-w + pointer.x, -w + newPointer.x).sorted()
                val (yStart, yEnd) = listOf(-s + pointer.y, -s + newPointer.y).sorted()
                for (i in xStart..xEnd) {
                    for (j in yStart..yEnd) {
//                        System.err.print("($i,$j);")
                        gridArray[j][i] = "*"
                    }
                }
//                System.err.print("\n")
            }
            pointer = newPointer
        }
        return gridArray.reversed().joinToString("\r\n") { it.joinToString("") }
    }

    /** Expand grid coordinates if [pointer] out of bounds*/
    private fun expand(pointer: Pointer) {
        when (pointer.orientation) {
            Orientation.N -> if (pointer.y > n) n = pointer.y
            Orientation.E -> if (pointer.x > e) e = pointer.x
            Orientation.S -> if (pointer.y < s) s = pointer.y
            Orientation.W -> if (pointer.x < w) w = pointer.x
        }
    }

}

/** Parse a [code] string into a list of [Command] */
fun parseCode(code: String): List<Command> = Regex("([FRL])([0-9]+)")
    .replace(code) { it.groupValues[1].repeat(it.groupValues[2].toInt()) }
    .let { Regex("F+|R+|L+").findAll(it) }
    .map { Command(RS1.valueOf("${it.value[0]}"), it.value.length) }
    .toList()

/** Parse [code] and return [String] representation of path along [Grid] */
fun execute(code: String): String {
//    System.err.println("-".repeat(10) + "\n")
    val commands = parseCode(code)
//    System.err.println("commands=$commands")
    val grid = Grid.sketch(commands)
    return grid.draw(commands)
}