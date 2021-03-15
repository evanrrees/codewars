import java.math.BigInteger

object Finance {
    fun finance(n: Int): BigInteger =
        n.toBigInteger() * (n + 1).toBigInteger() * (n + 2).toBigInteger() / (2).toBigInteger()
    fun finance2(n: Int): BigInteger =
        listOf(n, n + 1, n + 2, 2).map { it.toBigInteger() }.let { (a, b, c, d) -> a * b * c / d }
    fun finance3(n: Int): BigInteger =
        listOf(n, n + 1, n + 2, 2).map { it.toBigInteger() }.run { take(3).reduce { a, b -> a * b } / last() }
}