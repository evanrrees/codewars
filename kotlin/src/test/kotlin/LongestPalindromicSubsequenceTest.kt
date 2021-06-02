import org.junit.jupiter.api.Test
import LongestPalindromicSubsequence as LPS

import org.junit.jupiter.api.Assertions.*

internal class LongestPalindromicSubsequenceTest {

    @Test
    fun lps() {
        val seq = "AABCB"
        val mat = LPS.lps(seq)
        val expect = arrayOf(
            arrayOf(1, 2, 2, 2, 3),
            arrayOf(1, 1, 1, 3),
            arrayOf(1, 1, 3),
            arrayOf(1, 1),
            arrayOf(1)
        )
        println(mat.joinToString("\n") { it.toList().toString().padStart(mat.first().toList().toString().length) })
        println(expect.joinToString("\n") { it.toList().toString().padStart(expect.first().toList().toString().length) })
        assert(mat.contentDeepEquals(expect))
    }

    @Test
    fun longestProd() {
        val seq = "AABCB"
        val mat = LPS.lps(seq)
        val actual = LPS.lpsProd(mat)
        val expect = 6
        assertEquals(expect, actual)
    }

    @Test
    fun playWithWords() {
        var seq = "AABCB"
        var actual = LPS.playWithWords(seq)
        var expect = 6
        assertEquals(expect, actual)
        seq = "eeegeeksforskeeggeeks"
        actual = LPS.playWithWords(seq)
        expect = 50
        assertEquals(expect, actual)
    }

}