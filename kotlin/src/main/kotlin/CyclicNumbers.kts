tailrec fun gcd(a: Int, b: Int): Int = if (b == 0) a else gcd(b, a % b)

fun digitGen(n: Int): Int {
    if (gcd(n, 10) != 1) return -1
    var digit: Int = 10 / n
    var rem: Int = 10 % n
    val digits = mutableSetOf<Int>()
    while (!digits.contains(digit)) {
        while (digits.isEmpty() && digit == 0) {
            digit = 10 * rem / n
            rem = (10 * rem) % n
        }
        digits.add(digit)
        digit = 10 * rem / n
        rem = (10 * rem) % n
    }
    return digits.size
}

println(digitGen(5))
println(digitGen(13))
println(digitGen(21))
println(digitGen(27))
println(digitGen(33))
println(digitGen(37))
println(digitGen(94))