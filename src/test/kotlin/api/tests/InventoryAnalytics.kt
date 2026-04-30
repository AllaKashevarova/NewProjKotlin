package api.tests

object InventoryAnalytics {

    fun totalInventory(inventory: Map<String, Int>): Int =
        inventory.values.filter { it > 0 }.sum()

    fun statusesWithStock(inventory: Map<String, Int>): Set<String> =
        inventory.filterValues { it > 0 }.keys

    fun isInventoryHealthy(inventory: Map<String, Int>): Boolean =
        inventory.values.none { it < 0 }
}

