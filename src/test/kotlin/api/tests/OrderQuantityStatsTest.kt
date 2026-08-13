package api.tests

import api.model.Order
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class OrderQuantityStatsTest {

    @Test
    fun `should compute min max and average over present quantities`() {
        val orders = listOf(
            Order(id = 1L, quantity = 2),
            Order(id = 2L, quantity = 5),
            Order(id = 3L, quantity = 8),
        )

        val stats = OrderQuantityStatsBuilder.build(orders)

        assertEquals(3, stats.total)
        assertEquals(3, stats.withQuantity)
        assertEquals(0, stats.missingQuantity)
        assertEquals(2, stats.minQuantity)
        assertEquals(8, stats.maxQuantity)
        assertEquals(5.0, stats.averageQuantity)
    }

    @Test
    fun `should count orders with missing quantity separately`() {
        val orders = listOf(
            Order(id = 1L, quantity = 4),
            Order(id = 2L, quantity = null),
            Order(id = 3L),
        )

        val stats = OrderQuantityStatsBuilder.build(orders)

        assertEquals(3, stats.total)
        assertEquals(1, stats.withQuantity)
        assertEquals(2, stats.missingQuantity)
        assertEquals(4, stats.minQuantity)
        assertEquals(4, stats.maxQuantity)
        assertEquals(4.0, stats.averageQuantity)
    }

    @Test
    fun `should return null aggregates for empty input`() {
        val stats = OrderQuantityStatsBuilder.build(emptyList())

        assertEquals(0, stats.total)
        assertEquals(0, stats.withQuantity)
        assertEquals(0, stats.missingQuantity)
        assertNull(stats.minQuantity)
        assertNull(stats.maxQuantity)
        assertNull(stats.averageQuantity)
    }

    @Test
    fun `should return null aggregates when no order has a quantity`() {
        val stats = OrderQuantityStatsBuilder.build(listOf(Order(id = 1L), Order(id = 2L)))

        assertEquals(2, stats.total)
        assertEquals(0, stats.withQuantity)
        assertEquals(2, stats.missingQuantity)
        assertNull(stats.minQuantity)
        assertNull(stats.averageQuantity)
    }
}
