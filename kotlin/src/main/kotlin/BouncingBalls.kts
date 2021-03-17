import kotlin.math.log

fun bouncingBall(h: Double, b: Double, w: Double): Int {
    System.err.println("h: $h, b: $b, w: $w")
    return if (h <= 0.0 || b <= 0.0 || b >= 1.0 || w >= h) -1
    else log(w / h, b).let { if (it % 1.0 == 0.0) it.toInt() else it.toInt() * 2 + 1 }
}

fun bouncingBall2(h: Double, b: Double, w: Double): Int =
    log(w / h, b).let {
        when {
            it.isNaN() || it == 0.0 -> -1
            it % 1.0 == 0.0 -> it.toInt()
            else -> it.toInt() * 2 + 1
        }
    }


fun bouncingBall3(h: Double, b: Double, w: Double): Int =
    if (h <= 0.0 || b <= 0.0 || b >= 1.0 || w >= h) -1
    else bouncingBall3(h * b, b, w) + 2