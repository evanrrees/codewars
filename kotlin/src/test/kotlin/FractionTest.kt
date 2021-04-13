import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class FractionTest {

    @Test
    fun copy() {
        val unexpected = Fraction(1, 1)
        var actual = unexpected.copy()
        actual = actual + actual
        assertNotEquals(unexpected, actual)
        actual = unexpected.copy()
        unexpected.plusAssign(Fraction(1, 2))
        assertNotEquals(unexpected, actual)
    }

    @Test
    fun decimal() {
        assertEquals(Fraction(1, 2).decimal(), 0.5)
    }

    @Test
    fun plus() {
        val a = Fraction(1, 2)
        val b = Fraction(2, 3)
        val expect = Fraction(7, 6)
        assertEquals(expect, a + b)
    }

    @Test
    fun testPlus() {

    }

    @Test
    fun minus() {
        val a = Fraction(1, 2)
        val b = Fraction(2, 3)
        val expect = Fraction(-1, 6)
        assertEquals(expect, a - b)
    }

    @Test
    fun testMinus() {
    }

    @Test
    fun times() {
    }

    @Test
    fun div() {
    }

    @Test
    fun plusAssign() {
    }

    @Test
    fun minusAssign() {
    }

    @Test
    fun timesAssign() {
    }

    @Test
    fun divAssign() {
    }

    @Test
    fun compareTo() {
    }

    @Test
    fun simplify() {
        var actual = Fraction(4, 6)
        var expect = Fraction(2, 3)
        assertEquals(expect, actual.simplify())
        actual = Fraction(1, -1)
        expect = Fraction(-1, 1)
        assertEquals(expect, actual.simplify())
        actual = Fraction(-1, -1)
        expect = Fraction(1, 1)
        assertEquals(expect, actual.simplify())
    }

    @Test
    fun simplified() {
    }
}