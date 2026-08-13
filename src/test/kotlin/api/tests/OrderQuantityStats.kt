package api.tests

import api.model.Order

data class OrderQuantityStats(
    val total: Int,
    val withQuantity: Int,
    val missingQuantity: Int,
    val minQuantity: Int?,
    val maxQuantity: Int?,
    val averageQuantity: Double?,
)

object OrderQuantityStatsBuilder {

    fun build(orders: List<Order>): OrderQuantityStats {
        val quantities = orders.mapNotNull { it.quantity }

        return OrderQuantityStats(
            total = orders.size,
            withQuantity = quantities.size,
            missingQuantity = orders.size - quantities.size,
            minQuantity = quantities.minOrNull(),
            maxQuantity = quantities.maxOrNull(),
            averageQuantity = if (quantities.isEmpty()) null else quantities.average(),
        )
    }
}
