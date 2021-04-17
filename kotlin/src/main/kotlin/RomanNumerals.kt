import kotlin.IllegalArgumentException

class RomanNumeralException(c: Char) : IllegalArgumentException("'$c' is not a valid numeral")

// CDXLIV
// 400 + 40 + 4
// 444

object RomanNumerals {

    private val decodex = mapOf('I' to 1, 'V' to 5, 'X' to 10, 'L' to 50, 'C' to 100, 'D' to 500, 'M' to 1000)
    private val encodex = mapOf(1 to 'I', 5 to 'V', 10 to 'X', 50 to 'L', 100 to 'C', 500 to 'D', 1000 to 'M')
    fun <K, V> mapEntryOf(key: K, value: V) = mapOf(key to value).entries.single()
    fun <K, V> mapEntryOf(pair: Pair<K, V>) = mapOf(pair).entries.single()
    fun divmod(a: Int, b: Int): Pair<Int, Int> = a / b to a % b

    fun decode(s: CharSequence): Int {
        return s.asSequence()
            .map { decodex[it]?: throw RomanNumeralException(it) }
            .run { zipWithNext { a, b -> if (a >= b) a else -a }.sum() + last() }
    }

    fun encodeOther(n: Int): String {
        val one = "IXCM"
        val five = "VLD"
        val digits = "$n"
        val builder = StringBuilder()
        digits
        for (i in digits.indices) {
            val c = digits[digits.length - i - 1]
            builder.insert(0,
                when {
                    c < '4'     -> "${one[i]}".repeat(c - '0')
                    c == '4'    -> "${one[i]}${five[i]}"
                    c < '9'     -> "${five[i]}" + "${one[i]}".repeat(c - '0' - 5)
                    else        -> one.slice(i..i + 2)
                }
            )
        }
        return builder.toString()
    }

    fun encode(n: Int): String {
        val encodex = mapOf(1 to "I", 5 to "V", 10 to "X", 50 to "L", 100 to "C", 500 to "D", 1000 to "M")
        tailrec fun helper(n: Int, builder: StringBuilder, x: Int = 1000): String {
            if (n == 0 || x < 1) return builder.toString()
            val q = n / x
            builder.append(
                when (q) {
                    0       ->  ""
                    in 1..3 ->  encodex[x]?.repeat(q)
                    4, 9    ->  encodex[x] + encodex[x * 5 * (1 + q / 5)]
                    in 5..8 ->  encodex[x * 5] + encodex[x]?.repeat(q - 5)
                    else    ->  encodex[x * 10]?.repeat(q)
                }
            )
            return helper(n % x, builder, x / 10)
        }
        return helper(n, StringBuilder())
    }

    fun encodeDirty(n: Int): String {
        val codex = listOf('I' to 1, 'V' to 5, 'X' to 10, 'L' to 50, 'C' to 100, 'D' to 500, 'M' to 1000)

        tailrec fun helper(n: Int, builder: StringBuilder, i: Int): String {
            if (n == 0 || i < 0) return builder.toString()
            val (k, v) = codex[i]
            val (k2, v2) = codex.firstOrNull { (_, b) -> n % v + b >= v && b < v / 2 }?: null to null
            return if (n / v == 0 && k2 == null) helper(n, builder, i - 1)
            else {
                builder.append("$k".repeat(n / v) + (k2?.plus("$k")?: ""))
                helper(n % v + (v2 ?: v) - v, builder, i - 1)
            }
        }
        val builder = StringBuilder()
        with ("$n") {
            forEachIndexed { i, c ->
                val token = "$c".padEnd(length - i, '0').toInt()
                val startIndex = codex.indexOfFirst { it.second >= token || it.second == 1000 }
                builder.append(helper(token, StringBuilder(), startIndex))
            }
        }
        return builder.toString()
    }

}