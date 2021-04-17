/**
 * Return all combinations of elements in this collection
 *
 * @param iterable
 * @param iter
 * @param carry
 * @return
 */
tailrec fun <T> getCombinations(
    iterable: Iterable<T>,
    iter: Int = 0,
    carry: List<List<T>> = emptyList()
): List<List<T>> {
    if (iter >= iterable.count()) return carry
    val new = iterable.flatMap { c -> carry.map { it + c } }
    return getCombinations(iterable, iter + 1, new.ifEmpty { iterable.windowed(1) })
}
