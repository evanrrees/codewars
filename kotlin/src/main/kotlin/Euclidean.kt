/** Calculate greatest common denominator of [a] and [b] */
tailrec fun gcd(a: Long, b: Long): Long = if (b == 0L) a else gcd(b, a % b)

/** Calculate least common multiple of [a] and [b] */
fun lcm(a: Long, b: Long): Long = a * b / gcd(a, b)