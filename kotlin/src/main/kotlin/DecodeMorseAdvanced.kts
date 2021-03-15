object MORSE_CODE {
    private val morseEncode = mapOf(
        "A" to ".-", "B" to "-...", "C" to "-.-.", "D" to "-..", "E" to ".", "F" to "..-.", "G" to "--.", "H" to "....",
        "I" to "..", "J" to ".---", "K" to "-.-", "L" to ".-..", "M" to "--", "N" to "-.", "O" to "---", "P" to ".--.",
        "Q" to "--.-", "R" to ".-.", "S" to "...", "T" to "-", "U" to "..-", "V" to "...-", "W" to ".--", "X" to "-..-",
        "Y" to "-.--", "Z" to "--..", "1" to ".----", "2" to "..---", "3" to "...--", "4" to "....-", "5" to ".....",
        "6" to "-....", "7" to "--...", "8" to "---..", "9" to "----.", "0" to "-----", ", " to "--..--", "." to ".-.-.-",
        "?" to "..--..", "/" to "-..-.", "-" to "-....-", "(" to "-.--.", ")" to "-.--.-"
    )
    private val morseDecode = morseEncode.map { (k, v) -> v to k }.toMap()
    operator fun get(k: String): String? {
        return morseDecode[k]
    }
}

fun decodeBits(bits: String): String {
    val components = "(1+|0+)".toRegex()
        .findAll(bits.dropWhile { it == '0' }.dropLastWhile { it == '0' })
        .map { it.value }
    val rate = components.map { it.length }.min()?: 1
    val message = StringBuilder()
    val word = StringBuilder()
    val char = StringBuilder()
    components.map { it.take(it.length / rate) }
        .forEach {
            when (it) {
                "1" -> char.append('.')
                "111" -> char.append('-')
                "000" -> {
                    word.append(char)
                    word.append(" ")
                    char.clear()
                }
                "0000000" -> {
                    word.append(char)
                    message.append(word)
                    message.append("   ")
                    char.clear()
                    word.clear()
                }
            }
        }
    word.append(char)
    message.append(word)
    return message.toString()
}

fun decodeMorse(code: String): String =
    code.split("   ").joinToString(" ") {
        it.split(" ").map { c -> MORSE_CODE[c] }.joinToString("")
    }