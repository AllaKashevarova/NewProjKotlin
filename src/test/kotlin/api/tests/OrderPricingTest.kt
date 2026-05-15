package api.tests

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertTrue

class OrderPricingTest {

    @Test
    fun `should calculate line total in cents`() {
        assertEquals(3000, OrderPricing.lineTotalCents(quantity = 3))
        assertEquals(5000, OrderPricing.lineTotalCents(quantity = 5, unitPriceCents = 1000))
    }

    @Test
    fun `should derive total from order quantity`() {
        val order = OrderTestDataFactory.newPlacedOrder(quantity = 2)
        assertEquals(2000, OrderPricing.lineTotalCents(order))
    }

    @Test
    fun `should return null for invalid order quantity`() {
        val order = OrderTestDataFactory.newPlacedOrder(quantity = 0)
        assertNull(OrderPricing.lineTotalCents(order))
    }

    @Test
    fun `should check order against budget`() {
        val affordable = OrderTestDataFactory.newPlacedOrder(quantity = 2)
        val expensive = OrderTestDataFactory.newPlacedOrder(quantity = 50)

        assertTrue(OrderPricing.isWithinBudget(affordable, budgetCents = 5000))
        assertFalse(OrderPricing.isWithinBudget(expensive, budgetCents = 5000))
    }
}
