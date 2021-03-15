fun sum(mixed: List<Any>): Int {
    return mixed.map { it as Int }.sum()
}

sum(listOf(5, "5"))