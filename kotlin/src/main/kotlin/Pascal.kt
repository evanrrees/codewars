fun pascalFractionsRow(n: Int): Sequence<Pair<Int, Int>> =
    generateSequence(n to 1) { (a, b) -> a - 1 to b + 1 }.take(n)

fun pascalFractionsRow(n: Int, fraction: Boolean = true): Sequence<Fraction> =
    generateSequence(Fraction(n.toLong(), 1)) { f -> f + Fraction(-1, 1) }.take(n)

fun pascalFractionsDiag(n: Int): Sequence<Pair<Int, Int>> =
    generateSequence(n + 1 to 1) { (a, b) -> a + 1 to b + 1 }.take(n)

fun pascalRow(n: Int, k: Int) = pascalFractionsRow(n).take(minOf(k, n - k)).fold(1) { m, (a, b) -> m * a / b }