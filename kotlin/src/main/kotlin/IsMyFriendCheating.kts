fun removNb(n: Long): Array<LongArray> {
    val s = n * (n + 1) / 2 + 1
    return (2L..n)
        .asSequence()
        .filter { s % it == 0L }
        .map { longArrayOf(it - 1, s / it - 1) }
        .filter { it.last() < n }
        .toList()
        .toTypedArray()
}