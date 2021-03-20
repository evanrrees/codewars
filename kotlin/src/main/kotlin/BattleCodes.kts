fun battleCodes(armyLetters: String, armyNumbers: String): String {

    if (armyLetters.isEmpty() || armyNumbers.isEmpty()) return "Peace"

    tailrec fun fight(l: MutableList<Int>, n: MutableList<Int>): String {
        return if (l.isEmpty() && n.isEmpty()) "Draw"
        else if (l.isEmpty()) n.joinToString("")
        else if (n.isEmpty()) l.reversed().joinToString("") { "${(it + 96).toChar()}" }
        else {
            val nPower = n[0]
            val lPower = l[0]
            n[0] -= lPower
            l[0] -= nPower
            if (l.size > 1) l[1] -= nPower
            l.removeIf { it < 1 }
            n.removeIf { it < 1 }
            fight(l, n)
        }
    }

    val armyL = armyLetters.map { it.toInt() - 96 }.reversed().toMutableList()
    val armyN = armyNumbers.map { "$it".toInt() }.toMutableList()

    return fight(armyL, armyN)
}

fun battleCodes2(armyLetters: String, armyNumbers: String): String {

    if (armyLetters.isEmpty() && armyNumbers.isEmpty()) return "Draw"

    val armyL = armyLetters.map { it.toInt() - 96 }.reversed().toMutableList()
    val armyN = armyNumbers.map { "$it".toInt() }.toMutableList()

    while (armyL.isNotEmpty() && armyN.isNotEmpty()) {
        val nPower = armyN[0]
        val lPower = armyL[0]
        armyN[0] -= lPower
        armyL[0] -= nPower
        if (armyL.size > 1) {
            armyL[1] -= nPower
        }
        armyL.removeIf { it < 1 }
        armyN.removeIf { it < 1 }
    }

    return when {
        armyL.isEmpty() && armyN.isEmpty() -> "Draw"
        armyL.isEmpty() -> ""
        armyN.isEmpty() -> ""
        else -> ""
    }
}

battleCodes("abc", "1")
battleCodes("ah", "8")
battleCodes("abc", "123")
battleCodes("z", "99")
battleCodes("a", "")
