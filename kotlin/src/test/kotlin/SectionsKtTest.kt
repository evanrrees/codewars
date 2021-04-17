import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import kotlin.system.measureNanoTime
import kotlin.system.measureTimeMillis

internal class SectionsKtTest {



    @Test
    fun factorizeTime() {
        val nums = listOf<Long>(4096576, 2019, 10336225, 2961841, 13396105)
        val t1 = nums.flatMap { n -> (1..100).map { measureNanoTime { factorize(n) } } }
//        val t2 = nums.flatMap { n -> (1..100).map { measureNanoTime { factorize2(n) } } }
//        System.err.println("recursive:\t${t1.sum() / t1.size.toDouble()}\niterative:\t${t2.sum() / t2.size.toDouble()}")
    }

    @Test
    fun factorize() {
        println(factorize(1).groupingBy { it }.eachCount())
    }

    @Test
    fun factorize2() {
    }

    @Test
    fun c() {
        assertEquals(1, c(1))
        assertEquals(4, c(4))
        assertEquals(160, c(4096576))
        assertEquals(0, c(2019))
        assertEquals(16, c(10336225))
        assertEquals(4, c(2961841))
        assertEquals(0, c(13396105))
    }

    @Test
    fun c2() {
        assertEquals(1, c2(1))
        assertEquals(4, c2(4))
        assertEquals(160, c2(4096576))
        assertEquals(0, c2(2019))
        assertEquals(16, c2(10336225))
        assertEquals(4, c2(2961841))
        assertEquals(0, c2(13396105))
    }

}