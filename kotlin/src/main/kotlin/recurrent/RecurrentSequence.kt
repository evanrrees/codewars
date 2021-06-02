package recurrent

class RecurrentSequence<T>(vararg val seeds: T, val recurrence: RecurrentSequence<T>.(Int) -> T) : Sequence<T> {

    private val previousItems: MutableList<T> = mutableListOf(*seeds)

    operator fun get(index: Int): T {
        return if (index < seeds.size) previousItems[index]
        else {
            previousItems.add(recurrence(seeds.size))
            previousItems.removeAt(0)
            previousItems.last()
        }
    }

    override fun iterator(): Iterator<T> = object : Iterator<T> {

        init {
            seeds.forEachIndexed { i, t -> previousItems[i] = t }
        }

        var nextItem: T? = null
        var nextState: Int = -2 // -2 for initial unknown, -1 for next unknown, 0 for done, 1 for continue
        var cursor: Int = 0

        private fun calcNext() {
            nextItem = get(cursor++)
            nextState = if (nextItem == null) 0 else 1
        }

        override fun next(): T {
            if (nextState < 0) calcNext()
            if (nextState == 0) throw NoSuchElementException()
            val result = nextItem as T
            // Do not clean nextItem (to avoid keeping reference on yielded instance) -- need to keep state for getNextValue
            nextState = -1
            return result
        }

        override fun hasNext(): Boolean {
            if (nextState < 0) calcNext()
            return nextState == 1
        }
    }
}

fun <T> generateRecurrentSequence(vararg seeds: T, recurrence: RecurrentSequence<T>.(Int) -> T): RecurrentSequence<T> {
    return RecurrentSequence(*seeds, recurrence = recurrence)
}



inline fun <reified T> generateRecurrentSequence2(vararg initial: T, crossinline nextfunction: List<T>.(Int) -> T?): Sequence<T> {
    return sequence {
        val prev = mutableListOf(*initial)
        yieldAll(prev)
        val n = initial.size
        var next = prev.nextfunction(n)
        while (next is T) {
            yield(next as T)
            prev.add(next)
            prev.removeAt(0)
            next = prev.nextfunction(n)
        }
    }
}


fun <T> generateRecurrentSequence3(vararg initial: T, nextfunction: List<T>.(Int) -> T?): Sequence<T> {
    return sequence {
        val prev = mutableListOf(*initial)
        yieldAll(prev)
        val n = initial.size
        var next: T? = prev.nextfunction(n)
        while (next != null) {
            yield(next!!) // redundant but makes compiler happy?
            prev.add(next)
            prev.removeAt(0)
            next = prev.nextfunction(n)
        }
    }
}