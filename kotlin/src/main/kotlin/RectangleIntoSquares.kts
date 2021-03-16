import kotlin.math.abs

fun sqInRect(lng: Int, wdth: Int): List<Int>? =
    if (lng == wdth) null
    else {
        val result = mutableListOf<Int>()
        var a = minOf(wdth, lng)
        var b = maxOf(wdth, lng)
        var t: Int
        while (a > 0) {
            result.add(a)
            t = b
            b = maxOf(a, t - a)
            a = minOf(a, t - a)
        }
        result
    }

fun sqInRect2(lng: Int, wdth: Int): List<Int>? =
    if (lng == wdth) null
    else generateSequence(minOf(lng, wdth) to maxOf(lng, wdth)) { (a, b) ->
        minOf(a, b - a) to maxOf(a, b - a)
    }
        .takeWhile { it.first > 0 }
        .map { it.first }
        .toList()


tailrec fun sqInRect3(lng: Int, wdth: Int, squares: List<Int> = listOf()): List<Int>? = when {
    lng == wdth && squares.isEmpty() -> null
    lng == wdth -> squares + lng
    else -> sqInRect3(minOf(lng, wdth), abs(lng - wdth), squares + minOf(lng, wdth))
}