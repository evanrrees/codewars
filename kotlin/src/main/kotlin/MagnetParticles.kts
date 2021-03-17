import kotlin.math.pow

fun v(k: Int, n: Int): Double = 1 / (k * (n + 1.0).pow(2 * k))

fun u(k: Int, n: Int) = (1..n).sumByDouble { v(k, it) }

fun doubles(maxk: Int, maxn: Int): Double = (1..maxk).map { u(it, maxn) }.sum()