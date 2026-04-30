package api.tests

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
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
}

