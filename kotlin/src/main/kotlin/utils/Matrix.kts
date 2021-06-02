package utils

typealias Matrix<T> = Array<Array<T>>
operator fun <T> Matrix<T>.get(i: Int, j: Int) = this[i][j]
operator fun <T> Matrix<T>.set(i: Int, j: Int, value: T) {
    this[i][j] = value
}
fun <T> Matrix<T>.format(): String {
    val cellSize = flatMap { a -> a.map { b -> b.toString().length } }.max()?.plus(1) ?: 1
    return mapIndexed { i, a ->
        a.mapIndexed { j, b ->
            (if (j <= i) " " else "$b").padStart(cellSize)
        }.joinToString(" ")
    }.joinToString("\n")
}

fun <T> Matrix<T>.format(names: String): String {
    val cellSize = flatMap { a -> a.map { b -> b.toString().length } }.max()?.plus(1) ?: 1
    val colnames = names.split("").filterNot { it.isEmpty() }.joinToString(" ") { it.padStart(cellSize) }
    return format().lineSequence()
        .mapIndexed { i, s -> "${names[i]} | $s" }
        .joinToString("\n", prefix="  | $colnames\n")
}

inline fun <reified T> matrix(rows: Int, cols: Int, noinline init: (Int, Int) -> T): Matrix<T> =
    Array(rows) { i -> Array(cols) { j -> init(i, j) } }

val m = matrix(3, 3) { x, y -> x * y }
println(m.format("abc"))