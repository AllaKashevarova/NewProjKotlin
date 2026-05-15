package api.tests

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class InventoryReportTest {

    @Test
    fun `should build report with dominant status and stock summary`() {
        val inventory = mapOf("available" to 12, "pending" to 3, "sold" to 0, "damaged" to -1)
        val report = InventoryReportBuilder.build(inventory)

        assertEquals(15, report.totalStock)
        assertEquals(setOf("available", "pending"), report.statusesWithStock)
        assertEquals("available", report.dominantStatus)
        assertFalse(report.healthy)
    }

    @Test
    fun `should handle empty inventory`() {
        val report = InventoryReportBuilder.build(emptyMap())

        assertEquals(0, report.totalStock)
        assertTrue(report.statusesWithStock.isEmpty())
        assertTrue(report.healthy)
        assertEquals(null, report.dominantStatus)
    }
}
