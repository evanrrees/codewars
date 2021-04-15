import kotlin.math.sqrt

private const val ARRAY_MAX_SIZE = 64_000
private const val MAX_I = 252
private const val N_PRIMES = 6413

enum class LogLevel { Verbose, Debug }

data class LogMessage(val msg: String, val level: LogLevel = LogLevel.Debug)

object Logger {
    val buffer = mutableListOf<LogMessage>()
    fun log(msg: String, level: LogLevel = LogLevel.Debug) {
        buffer.add(LogMessage(msg, level))
    }
    fun print(level: LogLevel) {
        buffer.filter { it.level == level }
            .forEach { System.err.println(it.msg) }
    }
}

interface ISieve {
    fun isPrime(int: Int): Boolean
    fun factorize(int: Int)
}

object Sieve : ISieve {
    private val booleans: Array<Boolean> = Array(ARRAY_MAX_SIZE) { true }
    val primes: Array<Int> = Array(N_PRIMES) { 0 }
    init {
        booleans[0] = false
        booleans[1] = false
        (2..MAX_I).asSequence()
            .filter { booleans[it] }
            .flatMap { (it * it until ARRAY_MAX_SIZE step it).asSequence() }
            .forEach { booleans[it] = false }
        var j = 0
        booleans.indices.asSequence()
            .filter { booleans[it] }
            .forEach { primes[j++] = it }
    }
    override fun isPrime(int: Int): Boolean {
        return booleans.getOrNull(int)?: error("Only values between 0..$ARRAY_MAX_SIZE are supported ($int)")
    }
    override fun factorize(int: Int) {

    }
}

object SegmentedSieve {
    private const val ARRAY_MAX_SIZE = 64_000
    private const val SEGMENT_SIZE = 30_030     // product of 1st 6 primes < ARRAY_MAX_SIZE
    private val MAX_I = sqrt(SEGMENT_SIZE.toDouble()).toInt()
    private const val numSegments = 10
    //    private const val numSegments = Int.MAX_VALUE / 64_000
    private val segments = mutableListOf( Array(SEGMENT_SIZE) { true }.apply { this[0] = false; this[1] = false  } )
    val primes: Array<Int> = Array(ARRAY_MAX_SIZE) { 0 }
    //    private val primes = mutableListOf<Int>()
    private var mark = 0

    private fun segmentStart(s: Int, p: Int): Int {
        val r = (s * SEGMENT_SIZE) % p
        return if (r == 0) 0 else p - r
    }

    fun primeSequence(): Sequence<Int> = primes.asSequence().take(mark)
    fun primesToCheck(): Sequence<Int> = primes.asSequence().drop(6).take(mark)

    init {
        fun storePrimes(s: Int) {
            with(segments[s]) {
                indices.asSequence()
                    .filter { this[it] }
                    .forEach { primes[mark++] = it + SEGMENT_SIZE * s }
//                    .forEach { primes.add(mark++, it + SEGMENT_SIZE * s) }
            }
        }

        val template = Array(SEGMENT_SIZE) { true }
        (2..MAX_I).asSequence()
            .filter { segments[0][it] }
            .onEach {
                if (SEGMENT_SIZE % it == 0) {
                    for (i in 0 until SEGMENT_SIZE step it) template[it] = false
                }
            }
            .flatMap { (it * it until SEGMENT_SIZE step it).asSequence() }
            .forEach { segments[0][it] = false }
        storePrimes(0)

        Logger.log("template: ${template.toList()}")

        for (s in 1 until numSegments) {
            segments.add(s, template.copyOf())
            val maxP = sqrt(SEGMENT_SIZE.times(s + 1).toDouble()).toInt()
            Logger.log("segment: $s (${SEGMENT_SIZE * s}..${SEGMENT_SIZE * (s + 1) - 1}); maxP: $maxP", LogLevel.Verbose)
            with(segments[s]) {
                primesToCheck()
                    .takeWhile { it <= maxP }
                    .onEach { Logger.log("\tprime: $it") }
                    .flatMap { p -> (segmentStart(s, p) until SEGMENT_SIZE step p).asSequence() }
                    .onEach { Logger.log("\t\tmark: $it") }
                    .forEach { this[it] = false }
                storePrimes(s)
            }
        }
    }

    fun isPrime(int: Int): Boolean {
        TODO("Not yet implemented")
    }

    fun factorize(int: Int) {
        TODO("Not yet implemented")
    }
}