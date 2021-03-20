/** RS1 instruction */
enum class Instruction { F, R, L }

/** Command to execute the [instruction] instruction and specified number of [times] */
data class Command(val instruction: Instruction, val times: Int = 1) {
    override fun toString(): String = "$instruction$times"
}

/** Orientation for RS1 [Pointer], given in [x] and [y] coordinates */
enum class Orientation(val x: Int, val y: Int) {

    UP(0, 1), DOWN(0, -1), RIGHT(1, 0), LEFT(-1, 0);

    /** Process [command] and return an updated [Orientation] */
    fun update(command: Command): Orientation =
        when (command.instruction) {
            Instruction.R -> turnRight(command.times % 4)
            Instruction.L -> turnLeft(command.times % 4)
            Instruction.F -> this
        }

    /** Turn left [n] times and return a new [Orientation] */
    private fun turnLeft(n: Int = 1): Orientation =
        if (n == 0) this
        else when (this) {
            UP -> LEFT
            LEFT -> DOWN
            DOWN -> RIGHT
            RIGHT -> UP
        }.turnLeft(n - 1)

    /** Turn right [n] times and return a new [Orientation] */
    private fun turnRight(n: Int = 1): Orientation =
        if (n == 0) this
        else when (this) {
            UP -> RIGHT
            RIGHT -> DOWN
            DOWN -> LEFT
            LEFT -> UP
        }.turnRight(n - 1)

}

/** Store current state in [x] and [y] coordinates and an [orientation] */
data class Pointer(val x: Int = 0, val y: Int = 0, val orientation: Orientation = Orientation.RIGHT) {
    /** Process an RS1 [command] and return an updated [Pointer] */
    fun update(command: Command): Pointer {
        val o = orientation.update(command)
        var xNew = 0
        var yNew = 0
        if (command.instruction == Instruction.F) {
            xNew = o.x * command.times
            yNew = o.y * command.times
        }
        return Pointer(x + xNew, y + yNew, o)
    }
}

/** Holder for grid size as [yMax], [xMax], [yMin], [xMin]*/
class Grid(var yMax: Int = 0, var xMax: Int = 0, var yMin: Int = 0, var xMin: Int = 0) {

    override fun toString(): String = "(n: $yMax, s: $yMin, e: $xMax, w: $xMin)"

    companion object {
        /** Determine [Grid] size given a list of [commands]*/
        fun sketch(commands: List<Command>): Grid = Grid().apply {
            var pointer = Pointer()
            commands.forEach { cmd ->
                pointer = pointer.update(cmd)
                expand(pointer)
            }
//            System.err.println(this)
        }
    }

    /** Sketch and return a string representation of this [Grid] given a list of [commands] */
    fun draw(commands: List<Command>): String {
        var pointer = Pointer()
        val gridArray = Array(yMax - yMin + 1) { Array(xMax - xMin + 1) { " " } }
        gridArray[-yMin][-xMin] = "*"
        commands.forEach { cmd ->
//            System.err.println("$cmd, $pointer")
            val newPointer = pointer.update(cmd)
            if (cmd.instruction == Instruction.F) {
                val xStart = minOf(-xMin + pointer.x, -xMin + newPointer.x)
                val xEnd = maxOf(-xMin + pointer.x, -xMin + newPointer.x)
                val yStart = minOf(-yMin + pointer.y, -yMin + newPointer.y)
                val yEnd = maxOf(-yMin + pointer.y, -yMin + newPointer.y)
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
            Orientation.UP -> if (pointer.y > yMax) yMax = pointer.y
            Orientation.RIGHT -> if (pointer.x > xMax) xMax = pointer.x
            Orientation.DOWN -> if (pointer.y < yMin) yMin = pointer.y
            Orientation.LEFT -> if (pointer.x < xMin) xMin = pointer.x
        }
    }

}

/** Expand RS1 formatted [code] to linear */
fun expandRS1(code: String): String = Regex("([FRL])([0-9]+)")
    .replace(code) { it.groupValues[1].repeat(it.groupValues[2].toIntOrNull()?: 1) }

/** Expand RS2 formatted [code] to RS1 */
tailrec fun expandRS2(code: String): String {
    return if (!code.contains(Regex("[()]"))) { code }
    else {
        val temp = code.replace(Regex("\\(([FRL0-9]+)\\)([0-9]*)")) {
            it.groupValues[1].repeat(it.groupValues[2].toIntOrNull()?: 1)
        }
        expandRS2(temp)
    }
}

/** Tokenize RS1 formatted [code] to list of [Command]*/
fun tokenizeRS1(code: String): List<Command> = Regex("F+|R+|L+")
    .findAll(code)
    .map { Command(Instruction.valueOf("${it.value[0]}"), it.value.length) }
    .toList()

/** Tokenize RS2 formatted [code] to list of [Command]*/
fun tokenizeRS2(code: String): List<Command> = tokenizeRS1(expandRS1(expandRS2(code)))

/** Parse [code] and return [String] representation of path along [Grid] */
fun execute(code: String): String {
//    System.err.println("-".repeat(10) + "\n")
    val commands = tokenizeRS2(code)
//    System.err.println("commands=$commands")
    val grid = Grid.sketch(commands)
    return grid.draw(commands)
}