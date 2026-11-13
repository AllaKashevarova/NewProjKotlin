package api.tests

object InventoryAnalytics {

    fun totalInventory(inventory: Map<String, Int>): Int =
        positiveStock(inventory).values.sum()

    fun statusesWithStock(inventory: Map<String, Int>): Set<String> =
        positiveStock(inventory).keys

    fun isInventoryHealthy(inventory: Map<String, Int>): Boolean =
        inventory.values.none { it < 0 }

    fun stockShares(inventory: Map<String, Int>): Map<String, Double> {
        val positive = positiveStock(inventory)
        val total = positive.values.sum()
        if (total == 0) return emptyMap()

        return positive.mapValues { (_, count) -> count * 100.0 / total }
    }

    fun lowStockStatuses(inventory: Map<String, Int>, threshold: Int): Set<String> {
        require(threshold >= 0) { "threshold must not be negative" }
        return inventory.filterValues { it in 0 until threshold }.keys
    }

    private fun positiveStock(inventory: Map<String, Int>): Map<String, Int> =
        inventory.filterValues { it > 0 }
}
