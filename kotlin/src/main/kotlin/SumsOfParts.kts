fun sumParts(ls: IntArray): IntArray = ls.foldRight(intArrayOf(0)) { i: Int, acc: IntArray ->
    intArrayOf(acc.first().plus(i)) + acc
}

fun sumParts2(ls: IntArray): IntArray {
    for (i in ls.size - 2 downTo 0) {
        ls[i] += ls[i + 1]
    }
    return ls + 0
}

fun sumParts3(ls: IntArray): IntArray {
    (ls.size - 2 downTo 0).forEach { ls[it] += ls[it + 1] }
    return ls + 0
}