package recurrent

class RecurrentArray<T>(vararg seeds: T, private val recurrence: RecurrentArray<T>.(Int) -> T) : List<T>, Collection<T> {

    /** Last filled index of backing ArrayList */
    override val size: Int
        get() = pos + 1
    private var pos: Int = seeds.size
    private val arr: ArrayList<T> = arrayListOf(*seeds)

    constructor(vararg seeds: T, size: Int, recurrence: RecurrentArray<T>.(Int) -> T) : this(*seeds, recurrence = recurrence) {
        get(size)
    }

    /** Return element at specified index, filling backing array as necessary */
    override operator fun get(index: Int): T {
        if (index > pos) {
            for (i in pos..index) {
                arr.add(i, recurrence(i))
            }
            pos = index
        }
        return arr[index]
    }

    fun elementAt(index: Int): T {
        return get(index)
    }

    /** Fill array until [index] *
     *
     * @param index index of last element to generate
     */
    fun fillUntil(index: Int) {
        get(index)
    }

    override fun contains(element: T): Boolean {
        // TODO: this won't check if element appears in sequence, only if it's already been added
        for (i in 0..pos) if (arr[i] == element) return true
        return false
    }

    override fun containsAll(elements: Collection<T>): Boolean {
        // TODO: this won't check if element appears in sequence, only if it's already been added
        return elements.all { if (it in arr) arr.indexOf(it) <= pos else false }
    }

    override fun indexOf(element: T): Int {
        // TODO: this won't check if element appears in sequence, only if it's already been added
        return arr.indexOf(element).let { if (it <= pos) it else -1 }
    }

    override fun isEmpty() = arr.isEmpty()

    override fun iterator() = arr.iterator()

    fun iterator(until: Int): Iterator<T> {
        this[until]
        return arr.iterator()
    }

    override fun lastIndexOf(element: T) = pos

    override fun listIterator() = arr.listIterator()

    override fun listIterator(index: Int) = arr.listIterator(index)

    override fun subList(fromIndex: Int, toIndex: Int): List<T> {
        this[toIndex]
        return arr.subList(fromIndex, toIndex)
    }

    fun toList(): List<T> {
        arr.trimToSize()
        return arr.take(pos + 1)
    }

    companion object {
        fun <T> recurrentArrayOf(vararg seeds: T, recurrence: RecurrentArray<T>.(Int) -> T): RecurrentArray<T> {
            return RecurrentArray(*seeds, recurrence = recurrence)
        }
    }

}