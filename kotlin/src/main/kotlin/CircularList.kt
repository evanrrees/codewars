class CircularList<T>(vararg val elements: T) {
    init { require(elements.isNotEmpty()) }
    private var pos: Int = -1
        set(value) {
            field = when {
                value > elements.lastIndex -> 0
                value < 0 -> elements.lastIndex
                else -> value
            }
        }
    fun next() = elements[++pos]
    fun prev() = elements[--pos]
}