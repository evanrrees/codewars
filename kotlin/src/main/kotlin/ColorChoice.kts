import java.math.BigInteger

fun checkchoose(m: Long, n: Int) = (1L..n / 2 + n % 2).firstOrNull { n.toLong() choose it == BigInteger("$m") } ?: -1

infix fun Long.choose(x: Long): BigInteger = downTo(this - x + 1).fold(BigInteger.ONE) { p, i -> p * BigInteger("$i") }
    .div((1..x).fold(BigInteger.ONE) { p, i -> p * BigInteger("$i") })

fun checkchoose3(m: Long, n: Int): Long =
    generateSequence(1L to 1L) { (x, k) -> x * (n + 1 - k) / k to k + 1 }
        .take(n / 2 + 1)
        .firstOrNull { it.first == m }?.second?.minus(1L) ?: -1

fun checkchoose4(m: Long, n: Int) = generateSequence(1L to 1L) { (x, k) -> x * (n + 1 - k) / k to k + 1 }
    .take(n / 2 + 1).firstOrNull { it.first == m }?.second?.minus(1L) ?: -1

fun checkchoose2(m: Long, n: Long): Long {
    var x = n
    return (2L..n / 2 + n % 2)
        .asSequence()
        .onEach { x *= (n + 1 - it) }
        .onEach { x /= it }
        .firstOrNull { x == m } ?: -1
}

fun checkchoose1(m: Long, n: Int): Long {
    var x = n.toLong()
    (2L..n / 2 + n % 2).forEach { k ->
        x = x * (n + 1 - k) / k
        if (x == m) return k
    }
    return -1
}

fun pascalRow(n: Long, k: Long): Long {
    var x = n
    for (i in 2..k) x = x * (n + 1 - i) / i
    return x
}

fun pascalChoose(m: Long, n: Int): Long {
    var x = n.toLong()
    for (k in 2L..n / 2 + n % 2) {
        x = x * (n + 1 - k) / k
        if (x == m) return k
    }
    return -1
}