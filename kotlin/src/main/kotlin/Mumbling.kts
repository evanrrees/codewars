fun accum(s: String): String =
    s.toLowerCase().mapIndexed { i, c -> "$c".repeat(i + 1).capitalize() }.joinToString("-")