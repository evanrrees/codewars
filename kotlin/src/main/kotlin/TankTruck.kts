import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.acos
import kotlin.math.PI

fun tankVol(h: Int, d: Int, vt: Int): Int {
    val radius = d / 2.0
    val theta = acos((radius - h)/radius) * 2
    val segmentArea = (theta - sin(theta)) / 2 * radius.pow(2)
    val circleArea = PI * radius.pow(2)
    return (segmentArea / circleArea * vt).toInt()
}