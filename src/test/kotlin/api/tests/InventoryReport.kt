package api.tests

data class InventoryReport(
    val totalStock: Int,
    val statusesWithStock: Set<String>,
    val healthy: Boolean,
    val dominantStatus: String?,
)

object InventoryReportBuilder {

    fun build(inventory: Map<String, Int>): InventoryReport {
        val positiveEntries = inventory.filterValues { it > 0 }
        return InventoryReport(
            totalStock = InventoryAnalytics.totalInventory(inventory),
            statusesWithStock = InventoryAnalytics.statusesWithStock(inventory),
            healthy = InventoryAnalytics.isInventoryHealthy(inventory),
            dominantStatus = positiveEntries.maxByOrNull { it.value }?.key,
        )
    }
}
