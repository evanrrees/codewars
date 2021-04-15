/**
 * Return prime factorization of [num]
 * https://en.wikipedia.org/wiki/Wheel_factorization
 *
 * @param num
 * @return
 */
fun wheelFactors(num: Long): List<Long> {
    if (num == 1L) return listOf(num)
    var n = num
    val factors = mutableListOf<Long>()
    val inc = arrayOf(4, 2, 4, 2, 4, 6, 2, 6)
    for (k in listOf<Long>(2, 3, 5)) {
        while (n % k == 0L) {
            factors += k
            n /= k
        }
    }
    var k = 7L
    var i = 1
    while (k * k <= n) {
        if (n % k == 0L) {
            factors += k
            n /= k
        } else {
            k += inc[i]
            i = if (i < 8) i + 1 else 1
        }
    }
    if (n > 1) factors += n
    return factors
}