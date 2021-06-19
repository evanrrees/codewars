fun solution(input: String, markers: CharArray): String =
    input.lines().joinToString("\n") { line ->
        line.takeWhile { !markers.contains(it) }.trim()
    }

solution("apples, pears # and bananas\ngrapes\nbananas !apples", charArrayOf('#', '!'))