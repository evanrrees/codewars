enum class Color {
    PINK, RED, GREEN, ORANGE;
    override fun toString(): String = name.toLowerCase()
}

fun highlight(code: String): String {
    val span = { s: String, c: Color -> "<span style=\"color: $c\">$s</span>" }
    return Regex("(F+|R+|L+|[0-9]+|[()]+)")
        .findAll(code)
        .joinToString("") {
            it.groupValues[0].let { v ->
                when (v.first()) {
                    'F' -> span(v, Color.PINK)
                    'R' -> span(v, Color.GREEN)
                    'L' -> span(v, Color.RED)
                    in '0'..'9' -> span(v, Color.ORANGE)
                    else -> it.groupValues.first()
                }
            }
        }
}

// somebody else's nice sol'n :D
fun highlight2(code: String): String =
    Regex("F+|R+|L+|[0-9]+").replace(code) {
        val color = when (it.value[0]) {
            'F' -> "pink"
            'R' -> "green"
            'L' -> "red"
            else -> "orange"
        }
        "<span style=\"color: $color\">${it.value}</span>"
    }