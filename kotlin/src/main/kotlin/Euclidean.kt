const val SHORT_ZERO = 0.toShort()
const val SHORT_ONE = 1.toShort()

/** Calculate greatest common denominator of [a] and [b] */
fun gcd(a: Short, b: Short): Int = gcd(a.toInt(), b.toInt())
/** Calculate greatest common denominator of [a] and [b] */
tailrec fun gcd(a: Int, b: Int): Int = if (b == 0) a else gcd(b, a % b)
/** Calculate greatest common denominator of [a] and [b] */
tailrec fun gcd(a: Long, b: Long): Long = if (b == 0L) a else gcd(b, a % b)

/** Check if two numbers are coprime */
private tailrec fun coprime(a: Short, b: Short): Boolean = if (b == SHORT_ZERO) a == SHORT_ONE else coprime(b, (a % b).toShort())
/** Check if two numbers are coprime */
private tailrec fun coprime(a: Int, b: Int): Boolean = if (b == 0) a == 1 else coprime(b, a % b)
/** Check if two numbers are coprime */
private tailrec fun coprime(a: Long, b: Long): Boolean = if (b == 0L) a == 1L else coprime(b, a % b)


/** Calculate least common multiple of [a] and [b] */
fun lcm(a: Long, b: Long): Long = a * b / gcd(a, b)
/** Calculate least common multiple of [a] and [b] */
fun lcm(a: Int, b: Int): Int = a * b / gcd(a, b)
/** Calculate least common multiple of [a] and [b] */
fun lcm(a: Short, b: Short): Int = a * b / gcd(a.toInt(), b.toInt())
