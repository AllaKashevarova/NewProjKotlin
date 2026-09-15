package api.tests

import api.model.Order

object OrderCompletionChecker {

    fun isConsistent(order: Order): Boolean {
        val complete = order.complete ?: false
        val terminal = OrderStatusRules.isTerminal(order.status)

        return complete == terminal
    }

    fun findInconsistent(orders: List<Order>): List<Order> =
        orders.filterNot { isConsistent(it) }

    fun completionRate(orders: List<Order>): Double {
        if (orders.isEmpty()) return 0.0

        val completed = orders.count { it.complete == true }
        return completed.toDouble() / orders.size
    }
}
