import kotlin.math.sqrt

fun factorize(k: Long): List<Long> {
    val fac = mutableListOf<Long>()
    var n = k
    while (n % 2 == 0L) { fac += 2; n /= 2 }
    for (i in 3..sqrt(n.toDouble()).toLong() step 2) {
        while (n % i == 0L) { fac += i; n /= i }
    }
    if (n > 1) fac += n
    return fac
}

fun countPrimes(k: Long): Int {
    var ct = 1
    var n = k
    while (n % 2 == 0L) { ct += 1; n /= 2 }
    var i = 3
    while (n > 1) {
        var x = 1
        while (n % i == 0L) { n /= i; x++ }
        ct *= x; i++
    }
    return ct
}

fun c2(k: Long): Int {
    val sq = sqrt(k.toDouble())
    return if (sq % 1 == 0.0) countPrimes(sq.toLong() * k) else 0
}

fun c(k: Long): Int = when (k) {
    1L -> 1
    else -> with(factorize(k).groupingBy { it }.eachCount()) {
        if (any { it.value % 2 == 1 }) 0
        else values.map { it * 3 / 2 + 1 }.reduce(Int::times)
    }
}

fun pascalFractionsRow(n: Int) = generateSequence(n to 1) { (a, b) -> a - 1 to b + 1 }.take(n)
fun pascalRow(n: Int): List<Int> {
    var m = 1
    val result = mutableListOf(1)
    pascalFractionsRow(n).take(n / 2).forEach {
        m = m * it.first / it.second
        result += m
    }
    return result
}
// this works!!!! can be cleaned up
fun sol(x: Long): Int {
    return factorize(x).groupingBy { it }.eachCount().map { (_, v)-> pascalRow(v * 3 / 2).size * 2 }.reduce(Int::times)
}



fun formatFactors(f: List<Long>) = f.groupingBy { it }.eachCount().map { (k, v) -> "$k^$v" }
