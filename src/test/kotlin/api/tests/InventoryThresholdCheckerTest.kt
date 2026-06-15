package api.tests

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class InventoryThresholdCheckerTest {

    @Test
    fun `should detect statuses at or below threshold`() {
        val inventory = mapOf("available" to 2, "pending" to 0, "sold" to 50)
        val alerts = InventoryThresholdChecker.findLowStock(inventory, threshold = 5)

        assertEquals(2, alerts.size)
        assertEquals("pending", alerts.first().status)
        assertEquals(0, alerts.first().currentStock)
        assertTrue(InventoryThresholdChecker.hasLowStock(inventory, threshold = 5))
    }

    @Test
    fun `should report no low stock when all counts are above threshold`() {
        val inventory = mapOf("available" to 20, "sold" to 15)
        assertFalse(InventoryThresholdChecker.hasLowStock(inventory, threshold = 5))
    }
}
