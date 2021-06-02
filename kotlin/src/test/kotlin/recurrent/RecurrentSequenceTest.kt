package recurrent

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class RecurrentSequenceTest {

    @Test
    fun recurrentSequence() {
        val fibSequence = RecurrentSequence(0, 1) { get(it - 2) + get(it - 1) }
        assertEquals(fibSequence.take(5).toList(), listOf(0, 1, 1, 2, 3))
    }

    @Test
    fun iterator() {
        val fibIterator = RecurrentSequence(0, 1) { get(it - 2) + get(it - 1) }.iterator()
        val res = mutableListOf<Int>()
        while (fibIterator.hasNext()) {
            res += fibIterator.next()
            if (res.size > 10) break
        }
        assertEquals(listOf(0, 1, 1, 2, 3, 5, 8, 12, 21, 34, 55), res)
    }

    @Test
    fun generateRecurrentSequence() {
        val fibSequence = generateRecurrentSequence(0, 1) { get(it - 1) + get(it - 2) }
        val actual = fibSequence.take(10)
        val expect = sequenceOf(0, 1, 1, 2, 3, 5, 8, 13, 21, 34)
        assertEquals(expect.toList(), actual.toList())
    }

    @Test
    fun repeatRecurrentSequence() {
        val fibSequence = generateRecurrentSequence(0, 1) { get(it - 1) + get(it - 2) }
        val first = fibSequence.take(10)
        val second = fibSequence.take(10)
        assertEquals(first.toList(), second.toList())
    }

}