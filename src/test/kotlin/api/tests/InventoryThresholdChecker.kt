package api.tests

data class LowStockAlert(
    val status: String,
    val currentStock: Int,
    val threshold: Int,
)

object InventoryThresholdChecker {

    fun findLowStock(
        inventory: Map<String, Int>,
        threshold: Int,
    ): List<LowStockAlert> =
        inventory
            .filter { (_, count) -> count in 0..threshold }
            .map { (status, count) ->
                LowStockAlert(status = status, currentStock = count, threshold = threshold)
            }
            .sortedBy { it.currentStock }

    fun hasLowStock(inventory: Map<String, Int>, threshold: Int): Boolean =
        findLowStock(inventory, threshold).isNotEmpty()
}
