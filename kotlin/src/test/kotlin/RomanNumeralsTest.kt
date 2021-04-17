import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
internal class RomanNumeralsTest {

    @Test
    fun decode() {
        assertEquals(1, RomanNumerals.decode("I"))
        assertEquals(4, RomanNumerals.decode("IV"))
        assertEquals(6, RomanNumerals.decode("VI"))
        assertEquals(14, RomanNumerals.decode("XIV"))
        assertEquals(21, RomanNumerals.decode("XXI"))
        assertEquals(89, RomanNumerals.decode("LXXXIX"))
        assertEquals(91, RomanNumerals.decode("XCI"))
        assertEquals(984, RomanNumerals.decode("CMLXXXIV"))
        assertEquals(1000, RomanNumerals.decode("M"))
        assertEquals(1889, RomanNumerals.decode("MDCCCLXXXIX"))
        assertEquals(1989, RomanNumerals.decode("MCMLXXXIX"))
    }

    @Test
    fun encode() {
        assertEquals("", RomanNumerals.encode(0))
        assertEquals("I", RomanNumerals.encode(1))
        assertEquals("XXI", RomanNumerals.encode(21))
        assertEquals("MMVIII", RomanNumerals.encode(2008))
        assertEquals("MDCLXVI", RomanNumerals.encode(1666))
        assertEquals("MMCCXC", RomanNumerals.encode(2290))
        assertEquals("IV", RomanNumerals.encode(4))
        assertEquals("CDXLIV", RomanNumerals.encode(444))
    }

    @Test
    fun encodeOther() {
        assertEquals("", RomanNumerals.encodeOther(0))
        assertEquals("I", RomanNumerals.encodeOther(1))
        assertEquals("XXI", RomanNumerals.encodeOther(21))
        assertEquals("MMVIII", RomanNumerals.encodeOther(2008))
        assertEquals("MDCLXVI", RomanNumerals.encodeOther(1666))
        assertEquals("MMCCXC", RomanNumerals.encodeOther(2290))
        assertEquals("IV", RomanNumerals.encodeOther(4))
        assertEquals("CDXLIV", RomanNumerals.encodeOther(444))
        assertEquals("MMCMLIV", RomanNumerals.encodeOther(2954))
        assertEquals("MCMLIV", RomanNumerals.encodeOther(1954))
    }

    @Test
    fun encodeHard() {
        assertEquals("MMCMLIV", RomanNumerals.encode(2954))
        assertEquals("MCMLIV", RomanNumerals.encode(1954))
    }

}