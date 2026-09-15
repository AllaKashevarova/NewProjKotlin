package api.tests

import api.model.Order
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class OrderCompletionCheckerTest {

    private fun order(
        status: String? = OrderStatusRules.PLACED,
        complete: Boolean? = false,
    ): Order = Order(id = 1, petId = 1, quantity = 1, status = status, complete = complete)

    @Test
    fun `should accept delivered order marked complete`() {
        assertTrue(OrderCompletionChecker.isConsistent(order(status = OrderStatusRules.DELIVERED, complete = true)))
    }

    @Test
    fun `should accept placed and approved orders not marked complete`() {
        assertTrue(OrderCompletionChecker.isConsistent(order(status = OrderStatusRules.PLACED, complete = false)))
        assertTrue(OrderCompletionChecker.isConsistent(order(status = OrderStatusRules.APPROVED, complete = false)))
    }

    @Test
    fun `should reject non-delivered order marked complete`() {
        assertFalse(OrderCompletionChecker.isConsistent(order(status = OrderStatusRules.PLACED, complete = true)))
    }

    @Test
    fun `should reject delivered order not marked complete`() {
        assertFalse(OrderCompletionChecker.isConsistent(order(status = OrderStatusRules.DELIVERED, complete = false)))
    }

    @Test
    fun `should treat null complete flag as false`() {
        assertTrue(OrderCompletionChecker.isConsistent(order(status = OrderStatusRules.PLACED, complete = null)))
        assertFalse(OrderCompletionChecker.isConsistent(order(status = OrderStatusRules.DELIVERED, complete = null)))
    }

    @Test
    fun `should normalize status case and whitespace via status rules`() {
        assertTrue(OrderCompletionChecker.isConsistent(order(status = "  Delivered ", complete = true)))
    }

    @Test
    fun `should find only inconsistent orders`() {
        val consistent = order(status = OrderStatusRules.DELIVERED, complete = true)
        val inconsistent = order(status = OrderStatusRules.PLACED, complete = true)

        val result = OrderCompletionChecker.findInconsistent(listOf(consistent, inconsistent))

        assertEquals(listOf(inconsistent), result)
    }

    @Test
    fun `should return empty list when all orders are consistent`() {
        val orders = listOf(
            order(status = OrderStatusRules.PLACED, complete = false),
            order(status = OrderStatusRules.DELIVERED, complete = true),
        )
        assertTrue(OrderCompletionChecker.findInconsistent(orders).isEmpty())
    }

    @Test
    fun `completion rate should be fraction of completed orders`() {
        val orders = listOf(
            order(complete = true),
            order(complete = false),
            order(complete = null),
            order(complete = true),
        )
        assertEquals(0.5, OrderCompletionChecker.completionRate(orders))
    }

    @Test
    fun `completion rate should be zero for empty list`() {
        assertEquals(0.0, OrderCompletionChecker.completionRate(emptyList()))
    }
}
