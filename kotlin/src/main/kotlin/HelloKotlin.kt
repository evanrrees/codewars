val messageArray: Array<String> = arrayOf("Hello", "world")

fun formatMessage(args: Array<String>): String {
    return args[0] + ", " + args[1] + "!"
}

fun formatMessage2(): String {
    return messageArray.joinToString()
}

fun main() {
    val formattedMessage = formatMessage(messageArray)
    println(formattedMessage)
}