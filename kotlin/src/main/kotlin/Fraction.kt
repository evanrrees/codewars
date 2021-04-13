class Fraction(var num: Long, var denom: Long): Number(), Comparable<Fraction> {

    /**
     * Simplify a fraction by [lcm] of numerator and denominator
     *
     * @return this [Fraction]
     */
    fun simplify(): Fraction {
        val f = gcd(num, denom)
        num /= f
        denom /= f
        if (denom < 0) {
            num *= -1
            denom *= -1
        }
        return this
    }

    fun simplified(): Fraction {
        return copy().simplify()
    }

    /** Return a copy of this [Fraction] */
    fun copy(): Fraction = Fraction(num, denom)

    fun decimal(): Double = num.toDouble() / denom

    /** Add the other value to this value and [simplify] the result */
    operator fun plus(other: Fraction): Fraction {
        val d = lcm(denom, other.denom)
        return Fraction(num * d / denom + other.num * d / other.denom, d).simplify()
    }

    operator fun plus(other: Number): Fraction {
        return when (other) {
            is Byte     -> Fraction(num + other * denom, denom)
            is Short    -> Fraction(num + other * denom, denom)
            is Int      -> Fraction(num + other * denom, denom)
            is Long     -> Fraction(num + other * denom, denom)
            is Float    -> Fraction(num + (other * denom).toLong(), denom)
            is Double   -> Fraction(num + (other * denom).toLong(), denom)
            else -> error("$other type not supported")
        }
    }

    /** Subtract the other value from this value and [simplify] the result */
    operator fun minus(other: Fraction): Fraction {
        val d = lcm(denom, other.denom)
        return Fraction(num * d / denom - other.num * d / other.denom, d).simplify()
    }

    operator fun minus(other: Number): Fraction {
        return when (other) {
            is Byte     -> Fraction(num - other * denom, denom)
            is Short    -> Fraction(num - other * denom, denom)
            is Int      -> Fraction(num - other * denom, denom)
            is Long     -> Fraction(num - other * denom, denom)
            is Float    -> Fraction(num - (other * denom).toLong(), denom)
            is Double   -> Fraction(num - (other * denom).toLong(), denom)
            else -> error("$other type not supported")
        }
    }

    /** Multiply this value by the other value and [simplify] the result */
    operator fun times(other: Fraction): Fraction {
        return Fraction(num * other.num, denom * other.denom).simplify()
    }
    /** Divide this value by the other value and [simplify] the result */
    operator fun div(other: Fraction): Fraction {
        return Fraction(num * other.denom, denom * other.num).simplify()
    }

    operator fun plusAssign(other: Fraction) {
        val d = lcm(denom, other.denom)
        num *= (d / denom)
        num += other.num * d / other.denom
        denom = d
        simplify()
    }

    operator fun minusAssign(other: Fraction) {
        val d = lcm(denom, other.denom)
        num *= (d / denom)
        num -= other.num * d / other.denom
        denom = d
        simplify()
    }

    operator fun timesAssign(other: Fraction) {
        num *= other.num
        denom *= other.denom
        simplify()
    }

    operator fun divAssign(other: Fraction) {
        num *= other.denom
        denom *= other.num
        simplify()
    }

    override fun toString(): String = "($num/$denom)"

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Fraction

//        if (-num == other.num && -denom == other.denom) return true
        if (num != other.num) return false
        if (denom != other.denom) return false

        return true
    }

    override fun hashCode(): Int {
        var result = num.hashCode()
        result = 31 * result + denom.hashCode()
        return result
    }

    override fun toByte(): Byte {
        return (num / denom).toByte()
    }

    override fun toChar(): Char {
        return (num / denom).toChar()
    }

    override fun toDouble(): Double {
        return num.toDouble() / denom
    }

    override fun toFloat(): Float {
        return num.toFloat() / denom
    }

    override fun toInt(): Int {
        return (num / denom).toInt()
    }

    override fun toLong(): Long {
        return num / denom
    }

    override fun toShort(): Short {
        return (num / denom).toShort()
    }

    override operator fun compareTo(other: Fraction): Int {
        return decimal().compareTo(other.decimal())
    }

}