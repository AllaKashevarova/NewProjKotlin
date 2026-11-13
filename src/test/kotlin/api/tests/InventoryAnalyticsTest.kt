package api.tests

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class InventoryAnalyticsTest {

    @Test
    fun `should calculate total from positive stock values only`() {
        val inventory = mapOf("available" to 12, "pending" to 3, "sold" to 0, "damaged" to -2)
        val total = InventoryAnalytics.totalInventory(inventory)
        assertEquals(15, total, "Expected total to include only positive stock counts")
    }

    @Test
    fun `should return statuses that currently have stock`() {
        val inventory = mapOf("available" to 5, "pending" to 0, "sold" to 7)
        val statuses = InventoryAnalytics.statusesWithStock(inventory)
        assertEquals(setOf("available", "sold"), statuses, "Expected statuses with positive stock only")
    }

    @Test
    fun `should flag inventory as unhealthy when negative values exist`() {
        val healthyInventory = mapOf("available" to 1, "pending" to 0)
        val unhealthyInventory = mapOf("available" to 1, "damaged" to -1)

        assertTrue(InventoryAnalytics.isInventoryHealthy(healthyInventory), "Expected healthy inventory")
        assertFalse(InventoryAnalytics.isInventoryHealthy(unhealthyInventory), "Expected unhealthy inventory")
    }

    @Test
    fun `should calculate percentage share of positive stock per status`() {
        val inventory = mapOf("available" to 75, "pending" to 25, "sold" to 0, "damaged" to -5)

        val shares = InventoryAnalytics.stockShares(inventory)

        assertEquals(setOf("available", "pending"), shares.keys, "Expected only statuses with stock")
        assertEquals(75.0, shares.getValue("available"), 0.0001)
        assertEquals(25.0, shares.getValue("pending"), 0.0001)
    }

    @Test
    fun `stock shares should add up to one hundred percent`() {
        val shares = InventoryAnalytics.stockShares(mapOf("a" to 1, "b" to 1, "c" to 1))

        assertEquals(100.0, shares.values.sum(), 0.0001)
    }

    @Test
    fun `stock shares should be empty when there is no stock`() {
        assertTrue(InventoryAnalytics.stockShares(emptyMap()).isEmpty())
        assertTrue(InventoryAnalytics.stockShares(mapOf("sold" to 0, "damaged" to -1)).isEmpty())
    }

    @Test
    fun `should find statuses below the stock threshold`() {
        val inventory = mapOf("available" to 10, "pending" to 2, "sold" to 0, "damaged" to -3)

        val low = InventoryAnalytics.lowStockStatuses(inventory, threshold = 5)

        assertEquals(setOf("pending", "sold"), low, "Expected non-negative statuses under threshold")
    }

    @Test
    fun `status exactly at threshold is not low stock`() {
        assertTrue(InventoryAnalytics.lowStockStatuses(mapOf("available" to 5), threshold = 5).isEmpty())
    }

    @Test
    fun `should reject negative low stock threshold`() {
        assertFailsWith<IllegalArgumentException> {
            InventoryAnalytics.lowStockStatuses(emptyMap(), threshold = -1)
        }
    }
}

