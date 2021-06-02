package utils

import recurrent.RecurrentArray
import java.math.BigInteger

open class NumberOverflowException(message: String = ""): ArithmeticException(message)
class LongOverflowException(message: String = ""): NumberOverflowException(message)
class BigIntegerOverflowException(message: String = ""): NumberOverflowException(message)
class IntOverflowException(message: String = ""): NumberOverflowException(message)
class ShortOverflowException(message: String = ""): NumberOverflowException(message)

data class PascalCell(val row: BigInteger, val col: BigInteger = BigInteger.ZERO, val coef: BigInteger = BigInteger.ONE) {
    fun next() = PascalCell(row, col + BigInteger.ONE, coef * (row - col) / (col + BigInteger.ONE))
}

data class PascalRow(val index: Int) {
    val coefs = Array<BigInteger>(index + 1) { BigInteger.ONE }
    val c = RecurrentArray(PascalCell(index.toBigInteger())) { get(it - 1).next() }
}

object PascalTriangle {

    internal fun generateRow(n: Int): Array<Int> {
        require(n < 30) { "n must be less than 30 due to integer overflow" }
        var num = n
        var denom = 1
        val row = Array(n + 1) { 1 }
        val ofs = n % 2
        for (i in 1..n/2) {
            row[i] *= row[i - 1] * num-- / denom++
            row[n - i + ofs] = row[i]
            if (row[i] < row[i - 1]) throw IntOverflowException("row[i] < row[i-1]: $i, ${row[i]}, ${row[i-1]}")
        }
        return row
    }

    internal fun generateRow(n: Long): Array<Long> {
        require(n < 62) { "n must be less than 62 due to integer overflow" }
        var num = n
        var denom = 1
        val row = Array(n.toInt() + 1) { 1L }
        val ofs = n.toInt() % 2
        for (i in 1..(n.toInt())/2) {
            row[i] *= row[i - 1] * num-- / denom++
            row[n.toInt() - i + ofs] = row[i]
            if (row[i] < row[i - 1]) throw LongOverflowException("row[i] < row[i-1]: $i, ${row[i]}, ${row[i-1]}")
        }
        return row
    }

    internal fun generateRow(n: BigInteger): Array<BigInteger> {
        require(n < 64_000.toBigInteger()) { "n must be less than 64_000 due to max array size" }
        var num = n
        var denom = BigInteger.ONE
        val row = Array(n.toInt() + 1) { BigInteger.ONE }
        val ofs = n.toInt() % 2
        for (i in 1..(n.toInt())/2) {
            row[i] *= row[i - 1] * num-- / denom++
            row[n.toInt() - i + ofs] = row[i]
            if (row[i] < row[i - 1]) throw BigIntegerOverflowException("row[i] < row[i-1]: $i, ${row[i]}, ${row[i-1]}")
        }
        return row
    }

    private fun getDiagFractions(n: Int): Sequence<Pair<Int, Int>> {
        return generateSequence(n + 1 to 1) { (a, b) -> a + 1 to b + 1 }.take(n)
    }

    internal fun chooseSeq(n: Int, k: Int): Long {
        require(k <= n) { "k must be <= n! (k: $k, n: $n)" }
        return when (k) {
            0 -> 1
            1 -> n.toLong()
            else -> getRowFractions(n).take(k).fold(1L) { c, (a, b) -> c * a / b }
        }
    }

    internal fun chooseIter(n: Long, k: Long): Long {
        require(k <= n) { "k must be <= n! (k: $k, n: $n)" }
        if (k == 0L) return 1L
        if (k == 1L) return n.toLong()
        var num: Long = n
        var denom = 1
        var prev: Long
        var result = 1L
        while (denom <= k) {
            prev = result
            result *= num--
            result /= denom++
            if (result < prev) throw LongOverflowException()
        }
        return result
    }

    internal fun chooseIter(n: Int, k: Int): Long {
        require(k <= n) { "k must be <= n! (k: $k, n: $n)" }
        if (k == 0) return 1L
        if (k == 1) return n.toLong()
        var num: Int = n
        var denom = 1
        var prev: Long
        var result = 1L
        while (denom <= k) {
            prev = result
            result *= num--
            result /= denom++
            if (result < prev) throw LongOverflowException()
        }
        return result
    }

    internal fun chooseBig(n: Long, k: Long): BigInteger {
        require(k <= n) { "k must be <= n! (k: $k, n: $n)" }
        if (k == 0L) return BigInteger.ONE
        if (k == 1L) return n.toBigInteger()
        var num = n.toBigInteger()
        var denom = BigInteger.ONE
        var prev: BigInteger
        var result = BigInteger.ONE
        while (denom <= k.toBigInteger()) {
            prev = result
            result *= num--
            result /= denom++
            if (result < prev) throw BigIntegerOverflowException()
        }
        return result
    }

    internal fun chooseBigInteger(n: BigInteger, k: BigInteger): BigInteger {
        require(k <= n) { "k must be <= n! (k: $k, n: $n)" }
        if (k == BigInteger.ZERO) return BigInteger.ONE
        if (k == BigInteger.ONE) return n
        var num = n
        var denom = BigInteger.ONE
        var result = BigInteger.ONE
        while (denom <= k) result = result * num-- / denom++
        return result
    }

    internal fun chooseBigInteger2(n: BigInteger, k: BigInteger): BigInteger {
        require(k <= n) { "k must be <= n! (k: $k, n: $n)" }
        if (k == BigInteger.ZERO) return BigInteger.ONE
        if (k == BigInteger.ONE) return n
        var num = n
        var denom = BigInteger.ONE
        var result = BigInteger.ONE
        while (denom <= k) result = result * num-- / denom++
        return result
    }

}

fun BigInteger.choose(other: BigInteger) {

}

fun choose(n: Int, k: Int) = PascalTriangle.chooseIter(n, k)
fun choose(n: Long, k: Long) = PascalTriangle.chooseIter(n, k)
fun getRow(n: Int): Array<BigInteger> {
    require(n <= 64_000) { "Exceeded maximum array size of 64_000" }
    return PascalTriangle.generateRow(n.toBigInteger())
}

fun getRowFractions(n: Int) = generateSequence(n to 1) { (a, b) -> a - 1 to b + 1 }.take(n)
fun getRowFractions(n: Long) = generateSequence(n to 1L) { (a, b) -> a - 1 to b + 1 }.take(n.toInt())