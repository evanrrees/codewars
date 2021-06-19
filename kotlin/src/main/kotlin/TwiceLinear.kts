fun dblLinear(n: Int): Int {
    val u = mutableSetOf(1)
    var yIndex = 0
    var zIndex = 0
    (0 until n).forEach { _ ->
        val yValue = 2 * u.elementAt(yIndex) + 1
        val zValue = 3 * u.elementAt(zIndex) + 1
        when {
            yValue < zValue -> { u.add(yValue); yIndex++ }
            yValue == zValue -> { u.add(yValue); yIndex++; zIndex++ }
            else -> { u.add(zValue); zIndex++ }
        }
    }
    return u.last()
}

//fun dblLinear(n: Int): Int {
//    val seq = mutableListOf(1)
//    var fromIndex = 0
//    var insertionPoint: Int
//    var x: Int
//    var x1: Int
//    var x2: Int
//    for (i in 0..n) {
//        x = seq.elementAt(i)
//        x1 = 2 * x + 1
//        x2 = 3 * x + 1
//        insertionPoint = seq.binarySearch(fromIndex) { it.compareTo(x) }
//        fromIndex = insertionPoint
//        if (i > 0 && insertionPoint < 0) {
//            insertionPoint = -(insertionPoint + 1)
//            seq.add(insertionPoint, x)
//        }
//        insertionPoint = seq.binarySearch(insertionPoint) { it.compareTo(x1) }
//        if (insertionPoint < 0) {
//            insertionPoint = -(insertionPoint + 1)
//            seq.add(insertionPoint, x1)
//        }
//        insertionPoint = seq.binarySearch(insertionPoint) { it.compareTo(x2) }
//        if (insertionPoint < 0) {
//            insertionPoint = -(insertionPoint + 1)
//            seq.add(insertionPoint, x2)
//        }
//    }
//    System.err.println(seq)
//    return seq.elementAt(n)
//}

dblLinear(10)