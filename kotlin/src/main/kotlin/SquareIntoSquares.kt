import kotlin.math.sqrt
import java.math.BigInteger.ONE
import java.math.BigInteger.ZERO
import java.math.BigInteger as BigInt
import kotlin.toBigInteger as toBigInt

object Decomp {

    fun decompose(n: Long): String {
        val path = mutableListOf<Long>()
        fun dfs(parent: Long, weight: Long): Boolean {
            if (weight == 0L) return true
            for (child in parent downTo 1) {
                if (child * child <= weight) {
                    path.add(child)
                    if (dfs(child - 1, weight - child * child)) return true
                    else path.removeAt(path.size - 1)
                }
            }
            return false
        }
        return if (dfs(n - 1, n * n)) path.reversed().joinToString(" ") else "null"
    }

    private infix fun BigInt.downTo(other: BigInt) = generateSequence(this) { it - ONE }.takeWhile { it >= other }

    fun decomposeB(n: Long): String {
        val path = mutableListOf<BigInt>()
        val target = (n * n).toBigInt()
        var weight = ZERO
        fun firstChildOf(x: BigInt) = minOf(sqrt((target - weight).toDouble()).toLong(), x.toLong() - 1).toBigInt()
        fun recursor(node: BigInt) {
            val squared = node.pow(2)
            weight += squared
            path += node
            if (weight == target) return
            if (weight < target && node != ONE) {
                for (child in firstChildOf(node) downTo ONE) {
                    recursor(child)
                    if (weight == target) return
                }
            }
            weight -= squared
            path.removeAt(path.size - 1)
        }
        for (parent in n.toBigInt() - ONE downTo ONE) {
            recursor(parent)
            if (weight == target) return path.sorted().joinToString(" ")
            else {
                if (path.size.toLong() == parent.toLong()) break
                path.clear()
                weight = ZERO
            }
        }
        return "null"
    }

    fun decompose3(n: Long): String {
        val path = mutableListOf<BigInt>()
        val target = BigInt.valueOf(n * n)
        var weight = BigInt.ZERO
        fun firstChildOf(node: BigInt) = minOf(sqrt((target - weight).toDouble()).toLong(), node.toLong() - 1)
        fun helper(node: BigInt) {
            val square = node.pow(2)
            weight += square
            path += node
            if (weight == target) return
            if (weight < target && node != BigInt.ONE) {
                for (child in firstChildOf(node) downTo 1) {
                    helper(BigInt.valueOf(child))
                    if (weight == target) return
                }
            }
            weight -= square
            path.removeAt(path.size - 1)
        }

        for (parent in n.toInt() - 1 downTo 1) {
            helper(BigInt.valueOf(parent.toLong()))
            if (weight == target) return path.sorted().joinToString(" ")
            else {
                if (path.size == parent) break
                path.clear()
                weight = BigInt.ZERO
            }
        }
        return "null"
    }

    fun decompose2(n: Long): String {
        val squares = Array(n.toInt()) { (it.toLong()).toBigInt().pow(2) }                       // pre-compute squares
        val path = mutableListOf<Int>()                                                     // store path
        val target = (n * n).toBigInt()                                                          // target square
        var weight = ZERO                                                             // sum of current path
        var csum = ZERO                                                               // memo for cumulative initializer
        val cumulative = Array(n.toInt()) { csum += (it.toLong()).toBigInt().pow(2); csum }      // cumulative sum of squares
        fun helper(node: Int) {
            weight += squares[node]
            path += node
            if (weight == target) return                    // success!
            if (weight < target &&                          // not too large
                node != 1 &&                                // has children
                weight + cumulative[node - 1] >= target)    // sum of children is sufficient
            {
                val firstChild = sqrt((target - weight).toDouble()).toInt()
                for (child in firstChild downTo 1) {        // visit children
                    helper(child)
                    if (weight == target) return            // break on success
                }
            }
            weight -= squares[node]                         // failure; step up
            path.removeAt(path.size - 1)
        }

        val lastParent = cumulative.indexOfFirst { it > target }            // no parents possible below this node
        if (lastParent == -1) return "null"
        for (parent in n.toInt() - 1 downTo lastParent) {
            helper(parent)
            if (weight == target) return path.sorted().joinToString(" ")    // success!
            else {                                                          // failure; reset and try next node
                path.clear()
                weight = ZERO
            }
        }
        return "null"
    }
}