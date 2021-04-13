import java.math.BigInteger

fun Long.choose(other: Long): Long {
    if (other == 0L || other == this) return 1
    var n = this
    for (k in 1L..other) {
        n = n * (n + 1 - k) / k
    }
    return n
}

fun chooseForBig(n: Long, k: Int): BigInteger {
    val bigK = BigInteger("$k")
    return if (k in 0..n) {
        var m = BigInteger.ONE
        for (ki in 1..minOf(k, (n - k).toInt())) {
            m = m * BigInteger.valueOf(n + 1 - ki) / bigK
        }
        m
    } else BigInteger.ZERO
}

fun chooseFor(n: Long, k: Int): Long =
    if (k in 0..n) {
        var m = 1L
        var mNew: Long
        for (ki in 1..minOf(k, (n - k).toInt())) {
            mNew = m * (n + 1 - ki) / ki
            if (mNew < m) throw error("Long overflow: chooseFor($n, $k); ki=$ki")
            m = mNew
        }
        m
    } else 0L

fun chooseArray(n: Long, k: Int): Long {
    if (k > n || k < 0) return 0L
    val kMin = minOf(k, (n - k).toInt())
    val memo = Array(kMin + 1) { 1L }
    if (kMin < 1) return memo[kMin]
    for (ki in 1..kMin) {
        memo[ki] = memo[ki - 1] * (n + 1 - ki) / ki
        if (memo[ki] < memo[ki - 1]) throw error("Long overflow: chooseArray($n, $k); ki=$ki")
    }
    return memo[kMin]
}

fun chooseRecursive(n: Long, k: Long): Long = when {
    k !in 0..n -> 0
    k == 0L || k == n -> 1
    k == 1L -> n
    k > n / 2 -> chooseRecursive(n, n - k)
    else -> chooseRecursive(n, k - 1) * (n + 1 - k) / k
}