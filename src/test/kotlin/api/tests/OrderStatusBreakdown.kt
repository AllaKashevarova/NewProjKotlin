package api.tests

import api.model.Order

data class OrderStatusBreakdown(
    val total: Int,
    val countsByStatus: Map<String, Int>,
    val unknownStatusCount: Int,
    val completedCount: Int,
    val totalUnits: Int,
) {
    val completionRate: Double
        get() = if (total == 0) 0.0 else completedCount.toDouble() / total
}

object OrderStatusBreakdownBuilder {

    fun build(orders: List<Order>): OrderStatusBreakdown {
        val countsByStatus = orders
            .mapNotNull { normalizedStatus(it) }
            .groupingBy { it }
            .eachCount()

        return OrderStatusBreakdown(
            total = orders.size,
            countsByStatus = countsByStatus,
            unknownStatusCount = orders.size - countsByStatus.values.sum(),
            completedCount = orders.count { it.complete == true },
            totalUnits = orders.sumOf { (it.quantity ?: 0).coerceAtLeast(0) },
        )
    }

    fun withStatus(orders: List<Order>, status: String): List<Order> {
        val target = status.trim().lowercase()
        return orders.filter { normalizedStatus(it) == target }
    }

    fun awaitingDelivery(orders: List<Order>): List<Order> =
        orders.filter { OrderStatusRules.isValid(it.status) && !OrderStatusRules.isTerminal(it.status) }

    fun isFullyDelivered(orders: List<Order>): Boolean =
        orders.isNotEmpty() && orders.all { OrderStatusRules.isTerminal(it.status) }

    private fun normalizedStatus(order: Order): String? =
        order.status
            ?.takeIf { OrderStatusRules.isValid(it) }
            ?.trim()
            ?.lowercase()
}
