import kotlin.math.pow

object Finance {

    /**
     * Amortization
     *
     * @param rate yearly interest as decimal
     * @param p amount borrowed; also principal
     * @param term number of monthly payments
     * @param at calculate annuity and balance at
     * @return
     */
    fun amort(rate: Double, p: Int, term: Int, at: Int): String {
        val r = rate / 12 / 100
        val c: Double = p * r / (1 - (1 + r).pow(-term))
        val pp = c * (1 + r).pow(at - 1 - term)
        var b = p.toDouble()
        for (x in 1..at) b -= c * (1 + r).pow(x - 1 - term)
        return "num_payment %d c %.0f princ %.0f int %.0f balance %.0f".format(at, c, pp, c - pp, b)
    }
}