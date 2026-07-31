package api.tests

import api.model.Order
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class OrderBatchSummaryTest {

    @Test
    fun `should return an empty summary for no orders`() {
        val summary = OrderBatchSummaryBuilder.build(emptyList())

        assertEquals(0, summary.total)
        assertEquals(0, summary.totalQuantity)
        assertEquals(0, summary.completedCount)
        assertEquals(emptyMap(), summary.byStatus)
        assertEquals(0, summary.unknownStatusCount)
    }

    @Test
    fun `should aggregate quantities and completion flags`() {
        val orders = listOf(
            Order(id = 1, quantity = 2, status = "placed", complete = false),
            Order(id = 2, quantity = 3, status = "delivered", complete = true),
            Order(id = 3, quantity = null, status = "delivered", complete = true),
        )

        val summary = OrderBatchSummaryBuilder.build(orders)

        assertEquals(3, summary.total)
        assertEquals(5, summary.totalQuantity)
        assertEquals(2, summary.completedCount)
        assertEquals(mapOf("placed" to 1, "delivered" to 2), summary.byStatus)
    }

    @Test
    fun `should count blank and missing statuses as unknown`() {
        val orders = listOf(
            Order(id = 1, status = null),
            Order(id = 2, status = "   "),
            Order(id = 3, status = "PLACED"),
        )

        val summary = OrderBatchSummaryBuilder.build(orders)

        assertEquals(2, summary.unknownStatusCount)
        assertEquals(mapOf("placed" to 1), summary.byStatus)
    }
}
