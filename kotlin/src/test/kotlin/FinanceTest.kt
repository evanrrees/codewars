import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class FinanceTest {

    private fun testing(rate: Double, bal: Int, term: Int, num_payments: Int, expected: String) {
        val actual = Finance.amort(rate, bal, term, num_payments)
        assertEquals(expected, actual)
    }

    @Test
    fun test1() {
        testing(7.4, 10215, 24, 20, "num_payment 20 c 459 princ 445 int 14 balance 1809")
        testing(7.9, 107090, 48, 41, "num_payment 41 c 2609 princ 2476 int 133 balance 17794")
        testing(6.8, 105097, 36, 4, "num_payment 4 c 3235 princ 2685 int 550 balance 94447")
        testing(3.8, 48603, 24, 10, "num_payment 10 c 2106 princ 2009 int 98 balance 28799")
        testing(1.9, 182840, 48, 18, "num_payment 18 c 3959 princ 3769 int 189 balance 115897")
    }
}

