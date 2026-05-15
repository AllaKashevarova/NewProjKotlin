package api.tests

import api.client.StoreApiClient
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class StoreInventoryAnalyticsFlowTest {

    private val client = StoreApiClient()

    @Test
    fun `should build healthy inventory report from live store data`() = runBlocking {
        val inventory = client.getInventory()
        assertTrue(inventory.isNotEmpty(), "Expected non-empty inventory from Petstore API")

        val report = InventoryReportBuilder.build(inventory)

        assertTrue(report.healthy, "Live inventory should not contain negative stock counts")
        assertTrue(report.totalStock >= 0, "Total stock should be non-negative")
        assertNotNull(report.dominantStatus, "Expected at least one status with positive stock")
        assertTrue(
            report.statusesWithStock.contains(report.dominantStatus),
            "Dominant status should be included in statuses with stock",
        )
    }
}
