package api.tests

import api.client.StoreApiClient
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class StoreInventoryThresholdFlowTest {

    private val client = StoreApiClient()

    @Test
    fun `should analyze live inventory for low stock statuses`() = runBlocking {
        val inventory = client.getInventory()
        assertTrue(inventory.isNotEmpty(), "Expected non-empty inventory from Petstore API")

        val report = InventoryReportBuilder.build(inventory)
        assertNotNull(report.dominantStatus, "Expected at least one dominant inventory status")

        val lowStockAlerts = InventoryThresholdChecker.findLowStock(inventory, threshold = 10)
        // Informational check: API may or may not have low-stock statuses at any moment.
        assertTrue(lowStockAlerts.all { it.currentStock <= it.threshold })
    }
}
