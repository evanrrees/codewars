import kotlin.math.ceil

fun digitsAverage(input: Int): Int = generateSequence("$input") {
    it.zipWithNext { a, b -> "${ceil(("$a".toInt() + "$b".toInt()) / 2.0).toInt()}" }.joinToString("")
}.first { it.length == 1 }.toInt()

fun digitsAverage2(input: Int): Int = generateSequence("$input".map {
    "$it".toInt() }) { it.zipWithNext { a, b -> ceil((a + b)/2.0).toInt() }
}.first { it.size == 1 }.first()