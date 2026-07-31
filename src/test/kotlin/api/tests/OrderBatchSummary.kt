package api.tests

import api.model.Order

data class OrderBatchSummary(
    val total: Int,
    val totalQuantity: Int,
    val completedCount: Int,
    val byStatus: Map<String, Int>,
    val unknownStatusCount: Int,
)

object OrderBatchSummaryBuilder {

    fun build(orders: List<Order>): OrderBatchSummary {
        val grouped = orders.groupingBy { order ->
            order.status?.trim()?.lowercase()?.ifBlank { null } ?: "unknown"
        }.eachCount()

        val unknownCount = grouped["unknown"] ?: 0
        val knownCounts = grouped.filterKeys { it != "unknown" }

        return OrderBatchSummary(
            total = orders.size,
            totalQuantity = orders.sumOf { it.quantity ?: 0 },
            completedCount = orders.count { it.complete == true },
            byStatus = knownCounts,
            unknownStatusCount = unknownCount,
        )
    }
}
