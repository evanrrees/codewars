class Graph {

    private val graph = Array(9) { mutableSetOf<Int>() }

    private fun addEdge(from: Int, to: Int) { graph[from].add(to); graph[to].add(from) }

    private fun dropEdge(from: Int, to: Int) { graph[from].remove(to); graph[to].remove(from) }

    private fun visit(node: Int, visitedArray: Array<Boolean>) {
        visitedArray[node] = true
        when (node) {
            1 -> if (!visitedArray[0] && !visitedArray[2]) addEdge(0, 2)
            3 -> if (!visitedArray[0] && !visitedArray[6]) addEdge(0, 6)
            4 -> {
                if (!visitedArray[1] && !visitedArray[7]) addEdge(1, 7)
                if (!visitedArray[3] && !visitedArray[5]) addEdge(3, 5)
                if (!visitedArray[0] && !visitedArray[8]) addEdge(0, 8)
                if (!visitedArray[2] && !visitedArray[6]) addEdge(2, 6)
            }
            5 -> if (!visitedArray[2] && !visitedArray[8]) addEdge(2, 8)
            7 -> if (!visitedArray[6] && !visitedArray[8]) addEdge(6, 8)
        }
//        return graph[node].filter { !visitedArray[it] }
    }

    private fun unvisit(node: Int, visitedArray: Array<Boolean>) {
        visitedArray[node] = false
        when (node) {
            1 -> dropEdge(0, 2)
            3 -> dropEdge(0, 6)
            4 -> { dropEdge(1, 7); dropEdge(3, 5); dropEdge(0, 8); dropEdge(2, 6) }
            5 -> dropEdge(2, 8)
            7 -> dropEdge(6, 8)
        }
    }

    fun depthLimitedPath(start: Int, depth: Int): Int {
        val visited = Array(9) { false }
        var pathCount = 0
        fun depthLimitedHelper(start: Int, depth: Int, visited: Array<Boolean>) {
            if (depth == 0) pathCount += 1
            else if (depth > 0){
                visit(start, visited)
                for (child in graph[start]) {
                    if (!visited[child]) depthLimitedHelper(child, depth - 1, visited)
                }
                unvisit(start, visited)
            }
        }
        depthLimitedHelper(start, depth - 1, visited)
        return pathCount
    }

    init {
        addEdge(0, 1); addEdge(0, 5); addEdge(0, 4); addEdge(0, 7); addEdge(0, 3)
        addEdge(1, 2); addEdge(1, 5); addEdge(1, 8); addEdge(1, 4); addEdge(1, 6); addEdge(1, 3)
        addEdge(2, 5); addEdge(2, 7); addEdge(2, 4); addEdge(2, 3)
        addEdge(3, 4); addEdge(3, 8); addEdge(3, 7); addEdge(3, 6)
        addEdge(4, 5); addEdge(4, 6); addEdge(4, 7); addEdge(4, 8)
        addEdge(5, 8); addEdge(5, 7); addEdge(5, 6)
        addEdge(6, 7)
        addEdge(7, 8)
    }
}

fun countPatternsFrom(firstPoint: String, length: Int): Int {
    if (length !in 1..9 || firstPoint[0] !in 'A'..'I') return 0
    val startIndex = firstPoint.first().toInt() - 65
    return Graph().depthLimitedPath(startIndex, length)
}

println(countPatternsFrom("E", 4))