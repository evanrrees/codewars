object TimeFormatter {
    fun formatDuration(seconds: Int): String {
        val components = mutableMapOf<String, Int>().apply {
            var s = seconds
            set("days", s / (24 * 3600))
            s %= (24 * 3600)
            set("hours", s / 3600)
            s %= 3600
            set("minutes", s / 60)
            s %= 60
            set("seconds", s)
        }
            .filterValues { it > 0 }
            .map { (k, v) -> "$v ${if (v == 1) k.dropLast(1) else k}" }

        return when (components.size) {
            4 -> "${components[0]}, ${components[1]}, ${components[2]} and ${components[3]}"
            3 -> "${components[0]}, ${components[1]} and ${components[2]}"
            2 -> "${components[0]} and ${components[1]}"
            1 -> components[0]
            else -> "now"
        }
    }
}