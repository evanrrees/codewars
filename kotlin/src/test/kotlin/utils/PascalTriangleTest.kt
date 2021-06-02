package utils

import org.junit.jupiter.api.Test
import utils.PascalTriangle.chooseIter
import kotlin.system.measureNanoTime

internal class PascalTriangleTest {
    @Test
    fun chooseSeq() {
        val nList = listOf(10, 20, 30, 40)
        val res = nList.flatMap { n -> (0..n / 2).map { k -> measureNanoTime { PascalTriangle.chooseSeq(n, k) } } }
        System.err.println("Average time: ${res.sum() / res.size}")
    }

    @Test
    fun chooseIterTest() {
        val nList = listOf(10, 20, 30, 40)
        val res = nList.flatMap { n -> (0..n / 2).map { k -> measureNanoTime { chooseIter(n, k) } } }
        System.err.println("Average time: ${res.sum() / res.size}")
    }

    @Test
    fun chooseTimings() {
        val nList = listOf(10, 20, 30, 40)
        val res1 = nList.flatMap { n -> (0..n / 2).map { k -> measureNanoTime { PascalTriangle.chooseSeq(n, k) } } }
        val res2 = nList.flatMap { n -> (0..n / 2).map { k -> measureNanoTime { chooseIter(n, k) } } }
        val res3 = nList.flatMap { n -> (0L..n / 2).map { k -> measureNanoTime { PascalTriangle.chooseBig(n.toLong(), k) } } }
        System.err.println("method\tavg time (ns)")
        System.err.println("chooseSeq\t${res1.sum() / res1.size}")
        System.err.println("chooseIter\t${res2.sum() / res2.size}")
        System.err.println("chooseBig\t${res3.sum() / res3.size}")
    }

    @Test
    fun foo() {
        System.err.println(getRowFractions(10).joinToString { (a, b) -> "($a/$b)" })
    }

    @Test
    fun generateRow() {
        System.err.println(PascalTriangle.generateRow(100).toList())
    }

    @Test
    fun generateRowTimings() {
        val res = (0..100).map {
            measureNanoTime { repeat(100) { PascalTriangle.generateRow(it) } }
        }
        System.err.println(res)
    }

    @Test
    fun pascalRowOverflowLimit() {
        for (i in 0..29) {
            try {
                PascalTriangle.generateRow(i)
            } catch (e: IntOverflowException) {
                System.err.println("Integer overflow at n=$i")
                break
            }
        }
        for (i in 0..61L) {
            try {
                PascalTriangle.generateRow(i)
            } catch (e: LongOverflowException) {
                System.err.println("Long overflow at n=$i")
                break
            }
        }
    }

    @Test
    fun pascalBigIntegerTimings() {
        // timings and a cool histogram
        val t = (0..64_000 step 4000).map { measureNanoTime { getRow(it) } }
        val norm = t.map { it.toDouble() / (t.max()?: 1) }
        val width = 80
        val s = norm.mapIndexed { i, l -> "${i * 4000}".padStart(8, ' ') + " | " + "*".repeat((l * width).toInt()) }
            .joinToString("\n")
        val labels = (0..(t.max()?: 1) step (t.max()?: 1)/4).drop(1).joinToString(prefix = " ".repeat(11) + "0", separator = "") {
            "%.0f".format(it/1e6).padStart(18, ' ').padEnd(20, ' ')
        }.trimEnd()
        System.err.println(s)
        System.err.println(labels)
    }


}