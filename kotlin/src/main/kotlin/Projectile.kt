import kotlin.math.*

class Projectile(private val h0: Int, v0: Int, a0: Int) {

    private val vy = v0 * sin(a0 * PI / 180)
    private val vx = v0 * cos(a0 * PI / 180)
    private val time = ((vy + sqrt(vy * vy + 64 * h0)) / 32)

    fun height(t: Double) = (-16 * t * t + vy * t + h0).rounded()
    fun horiz(t: Double) = (vx * t).rounded()
    fun heightEq(): String = "h(t) = -16.0t^2 + ${vy.rounded()}t" + if (h0 > 0.0) " + $h0.0" else ""
    fun horizEq(): String = "x(t) = ${vx.rounded()}t"
    fun landing() = doubleArrayOf(horiz(time), 0.0, time.rounded())

    companion object {
        fun Double.rounded() = round(this * 1000) / 1000
    }
}