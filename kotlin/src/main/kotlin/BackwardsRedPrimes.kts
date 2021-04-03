import kotlin.math.sqrt

fun sieve(n: Long): List<Long> {
    val arr = Array(n.toInt()) { true }
    for (i in 2..sqrt(n.toDouble()).toInt()) {
        if (arr[i]) for (j in i * i until n.toInt() step i) arr[j] = false
    }
    return arr.withIndex().drop(2).filter { it.value }.map { it.index.toLong() }
}

fun backwardsPrime(start: Long, end: Long):String {
    val backwards = (start..end).map { "$it".reversed().toLong() }
    val primes = sieve(end)
    val output = primes.dropWhile { it < start }
        .filter { "$it".reversed().toLong().let { x -> primes.contains(x) && x != it } }
    return if (output.isEmpty()) "0" else output.joinToString(" ")
}