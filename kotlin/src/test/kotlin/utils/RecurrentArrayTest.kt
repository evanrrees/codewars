package utils

import org.junit.jupiter.api.Test
import recurrent.RecurrentArray

internal class RecurrentArrayTest {

    @Test
    fun testOne() {
        val fib = RecurrentArray(0, 1) { get(it - 1) + get(it - 2) }
        val s = fib.subList(0, 10)
//        fib[10]
        fib.size
        fib[15]
        fib.size
        val fibit = fib.iterator(20)
        fib.size
        fibit.forEachRemaining {  }
        fib.size
    }

    @Test
    fun testTwo() {
        val fib = RecurrentArray(PascalCell(5.toBigInteger()), size = 5) { get(it - 1).next() }
        println(fib.size)
        println(fib.toList())
    }

}