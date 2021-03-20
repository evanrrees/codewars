object TimeFormatter {
    fun formatDuration(seconds: Int): String =
        if (seconds == 0) "now"
        else mutableMapOf<String, Int>().apply {
            var s = seconds
            set("years", s / (365 * 24 * 60 * 60))
            s %= (365 * 24 * 60 * 60)
            set("days", s / (24 * 60 * 60))
            s %= (24 * 60 * 60)
            set("hours", s / (60 * 60))
            s %= (60 * 60)
            set("minutes", s / 60)
            s %= 60
            set("seconds", s)
        }
            .filterValues { it > 0 }
            .map { (k, v) -> "$v ${if (v == 1) k.dropLast(1) else k}" }
            .run {
                if (size == 1) this
                else dropLast(2) + takeLast(2).let { (a, b) -> "$a and $b" }
            }
            .joinToString(", ")
}