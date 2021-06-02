object LongestPalindromicSubsequence {

    fun playWithWords(s: String): Int {
        val n = s.lastIndex
        val l = Array(n + 1) { Array(n - it + 1) { 1 } }
        for (j in 1..n) {
            for (i in 0..n - j) {
                l[i][j] = when {
                    s[i] == s[i + j] && j == 1  -> 2
                    s[i] == s[i + j]            -> l[i + 1][j - 2] + 2
                    else                        -> maxOf(l[i][j - 1], l[i + 1][j - 1])
                }
            }
        }
        return l[0].asSequence().take(n).foldIndexed(0) { i, acc, x -> maxOf(acc, x * l[i + 1].last()) }
    }

    fun lps(s: String): Array<Array<Int>> {
        val n = s.length
        val l = Array(n) { Array(n - it) { 1 } }
        for (j in 1..l.lastIndex) {                     // j tracks diagonal
            for (i in 0..l.lastIndex - j) {
                l[i][j] = when {
                    s[i] == s[i + j] && j == 1  -> 2
                    s[i] == s[i + j]            -> l[i + 1][j - 2] + 2                      // row below, column to left == diag below left
                    else                        -> maxOf(l[i][j - 1], l[i + 1][j - 1])  // max of same row left, same col below
                }
            }
        }
        return l
    }

    fun lpsProd(arr: Array<Array<Int>>): Int {
        val n = arr.lastIndex
        var current = 0
        for (i in 0 until n) {
            val p = arr[0][i] * arr[i + 1][n - i - 1]
            if (p > current) current = p
            if (p < current) break
        }
        return current
    }

    fun <T> Array<Array<T>>.format(): String {
        val cellSize = flatMap { a -> a.map { b -> b.toString().length } }.max()?.plus(1) ?: 1
        return mapIndexed { i, a ->
            a.mapIndexed { j, b ->
                (if (j < i) " " else "$b").padStart(cellSize)
            }.joinToString(" ")
        }.joinToString("\n")
    }

    fun <T> Array<Array<T>>.format(names: String): String {
        val cellSize = flatMap { a -> a.map { b -> b.toString().length } }.max()?.plus(1) ?: 1
        val colnames = names.split("").filterNot { it.isEmpty() }.joinToString(" ") { it.padStart(cellSize) }
        return format().lineSequence()
            .mapIndexed { i, s -> "${names[i]} | $s" }
            .joinToString("\n", prefix="  | $colnames\n")
    }

}