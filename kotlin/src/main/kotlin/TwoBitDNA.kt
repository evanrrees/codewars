class TwoBitDNASeq(val bytes: ByteArray, val stop: Int) {
    fun decode(): String {
        TODO()
    }

    companion object {

        private val encodingMap = mutableMapOf<String, Byte>()
        private val decodingMap = mutableMapOf<Byte, String>()
        private val complementMap = mutableMapOf<Byte, Byte>()

        private fun getEncoding(s: String): Byte {
            return encodingMap.getOrPut(s.toUpperCase()) {
                encode4mer(s).also { decodingMap.putIfAbsent(it, s) }
            }
        }

        private fun getDecoding(b: Byte): String {
            return decodingMap.getOrPut(b) {
                decodeByte(b).also { encodingMap.putIfAbsent(it, b) }
            }
        }

        fun encode4mer(s: CharSequence): Byte {
            require(s.matches(Regex("[ACGTUacgtu]+")))
            return s.fold(0) { b, x -> b shl 2 or (x.toInt() shr 1 and 3) }.toByte()
        }

        fun encode(s: String): TwoBitDNASeq {
            val r = s.length.rem(4)
            val bytes = s.plus(if (r != 0) "A".repeat(4 - r) else "")
                .windowed(4, 4, false) { encode4mer(it) }
                .toByteArray()
            return TwoBitDNASeq(bytes, r)
        }

        fun db(b: Byte): Int {
            return if (b < 0) b.toInt().xor(255).plus(1) * -1 else b.toInt()
        }

        fun complementByte(b: Byte): Byte {
            b.toInt()
            TODO()
        }

        private val decoding = arrayOf('A', 'C', 'T', 'G')
        private val decoding2 = arrayOf("A", "C", "T", "G")

        fun decodeByte(b: Byte): String {
            val ub = if (b < 0) b.toInt().xor(255) * -1 else b.toInt()
            return (6 downTo 0 step -2).joinToString { decoding2[ub shr it and 3] }
        }

        val complementMask = arrayOf(1, 5, 21, 85)

        fun complement(b: Byte, stop: Int = 0): Int {
            return b.toInt() xor complementMask[stop]
        }
    }
}