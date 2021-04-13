import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class EuclideanTest {

    @Test
    fun gcd() {
        var expect = 1L
        assertEquals(expect, gcd(11, 13))
        expect = 3
        assertEquals(expect, gcd(9, 6))
    }

    @Test
    fun lcm() {
        var expect = 11 * 13L
        assertEquals(expect, lcm(11, 13L))
        expect = 10
        assertEquals(expect, lcm(5, 10))
        expect = 12
        assertEquals(expect, lcm(6, 4))
        assertEquals(expect, lcm(4, 6))
    }
}