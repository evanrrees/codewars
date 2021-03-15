import java.math.BigInteger

object SumFct {
    private fun fibonacci(): Sequence<BigInteger> =
        generateSequence(BigInteger.ZERO to BigInteger.ONE) { (a, b) -> b to a + b }.map { it.second }
    fun perimeter(n: Int): BigInteger =
        fibonacci().take(n + 1).fold(BigInteger.ZERO) { s, it -> s + it } * BigInteger.valueOf(4L)
}