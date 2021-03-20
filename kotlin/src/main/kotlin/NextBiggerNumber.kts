fun nextBiggerNumber(n: Long): Long {
    val digits = "$n".map { "$it".toInt() }.toMutableList()
    val i = digits.zipWithNext().indexOfLast { (a, b) -> a < b }
    if (i == -1) return -1
    val j = digits.withIndex().drop(i + 1)
        .filter { it.value > digits[i] }
        .minBy { it.value }?.index ?: return -1
    val p = digits[i]
    digits[i] = digits[j]
    digits[j] = p
    return (digits.take(i + 1) + digits.drop(i + 1).sorted()).joinToString("").toLong()
}
