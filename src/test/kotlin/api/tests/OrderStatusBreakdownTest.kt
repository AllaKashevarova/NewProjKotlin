package api.tests

import api.model.Order
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class OrderStatusBreakdownTest {

    private fun order(status: String?, quantity: Int? = 1, complete: Boolean? = false): Order =
        Order(petId = 1L, quantity = quantity, status = status, complete = complete)

    @Test
    fun `should count orders per normalized status`() {
        val orders = listOf(
            order("placed"),
            order("PLACED"),
            order(" approved "),
            order("delivered", complete = true),
        )

        val breakdown = OrderStatusBreakdownBuilder.build(orders)

        assertEquals(4, breakdown.total)
        assertEquals(mapOf("placed" to 2, "approved" to 1, "delivered" to 1), breakdown.countsByStatus)
        assertEquals(0, breakdown.unknownStatusCount)
    }

    @Test
    fun `should report unknown and missing statuses separately`() {
        val orders = listOf(order("placed"), order("shipped"), order(null), order(""))

        val breakdown = OrderStatusBreakdownBuilder.build(orders)

        assertEquals(mapOf("placed" to 1), breakdown.countsByStatus)
        assertEquals(3, breakdown.unknownStatusCount)
    }

    @Test
    fun `should sum units while ignoring missing and negative quantities`() {
        val orders = listOf(order("placed", quantity = 3), order("placed", quantity = null), order("placed", quantity = -4))

        assertEquals(3, OrderStatusBreakdownBuilder.build(orders).totalUnits)
    }

    @Test
    fun `should compute completion rate`() {
        val orders = listOf(
            order("delivered", complete = true),
            order("placed", complete = false),
            order("approved", complete = null),
            order("delivered", complete = true),
        )

        val breakdown = OrderStatusBreakdownBuilder.build(orders)

        assertEquals(2, breakdown.completedCount)
        assertEquals(0.5, breakdown.completionRate, 0.0001)
    }

    @Test
    fun `should return zeroed breakdown for empty list`() {
        val breakdown = OrderStatusBreakdownBuilder.build(emptyList())

        assertEquals(0, breakdown.total)
        assertTrue(breakdown.countsByStatus.isEmpty())
        assertEquals(0.0, breakdown.completionRate, 0.0001)
    }

    @Test
    fun `should filter orders by status ignoring case and whitespace`() {
        val orders = listOf(order("placed"), order("Approved"), order("approved"))

        assertEquals(2, OrderStatusBreakdownBuilder.withStatus(orders, " APPROVED ").size)
        assertTrue(OrderStatusBreakdownBuilder.withStatus(orders, "delivered").isEmpty())
    }

    @Test
    fun `awaiting delivery should exclude delivered and unknown orders`() {
        val placed = order("placed")
        val approved = order("approved")
        val orders = listOf(placed, approved, order("delivered", complete = true), order("bogus"))

        assertEquals(listOf(placed, approved), OrderStatusBreakdownBuilder.awaitingDelivery(orders))
    }

    @Test
    fun `fully delivered requires a non-empty list of delivered orders`() {
        assertTrue(OrderStatusBreakdownBuilder.isFullyDelivered(listOf(order("delivered"), order("Delivered"))))
        assertFalse(OrderStatusBreakdownBuilder.isFullyDelivered(listOf(order("delivered"), order("placed"))))
        assertFalse(OrderStatusBreakdownBuilder.isFullyDelivered(emptyList()))
    }
}
