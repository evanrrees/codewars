import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class DecompTest {

    object Helper {
        fun string2LongArray(s: String): LongArray {
            return s.split(" ").map { it.toLong() }.toLongArray()
        }
        fun isSorted(a: LongArray): Boolean {
            return a.contentEquals(a.sortedArray())
        }
        fun total(a: LongArray, n: Long): Boolean {
            return a.fold(0L) { s, it -> s + it * it } == n
        }
    }

    private fun dotest(n: Long, sexpr: String) {
        println(n)
        val success: Boolean
        val sact: String = Decomp.decompose(n)
        val st: Boolean
        val t: Boolean
        System.out.printf("Expected %s and got %s\n", sexpr, sact, "\n")
        if ((sact == "null" && sexpr == "null") || (sact != "null" && sact == sexpr)) {
            System.out.printf("Same as Expected\n")
            success = true
        } else {
            if (sact == "null") {
                success = false
            } else {
                val intarr1: LongArray = Helper.string2LongArray(sact)
                st = Helper.isSorted(intarr1)
                t = Helper.total(intarr1, n * n)
                success = if (!st || !t) {
                    println("** Error. Not increasinly sorted or bad sum of squares **")
                    false
                } else {
                    println("Correct; Increasing and total correct")
                    true
                }
            }
        }
        assertEquals(true, success)
    }

    @Test
    fun testOne() {
        val expect = "null"
        val actual = Decomp.decompose(2)
        assertEquals(expect, actual)
    }

    @Test
    fun testTwo() {
        val expect = "1 2 4 10"
        val actual = Decomp.decompose(11)
        assertEquals(expect, actual)
    }

    @Test
    fun testThree() {
        val expect = "1 2 3 7 9"
        val actual = Decomp.decompose(12)
        assertEquals(expect, actual)
    }

    @Test
    fun testFour() {
        val expect = "null"
        val actual = Decomp.decompose(4)
        assertEquals(expect, actual)
    }

    @Test
    fun testFive() {
        val expect = "2 5 8 34 624"
        assertEquals(expect, Decomp.decompose(625))
    }

    @Test
    fun testOverflow() {
        Decomp.decompose(1234567)
    }

    @Test
    fun testTimeout() {
        Decomp.decompose(7654321)
    }

    @Test
    fun basicTests() {
        dotest(2, "null")
        dotest(11, "1 2 4 10")
        dotest(12, "1 2 3 7 9")
        dotest(4, "null")
        dotest(625, "2 5 8 34 624")
    }
}