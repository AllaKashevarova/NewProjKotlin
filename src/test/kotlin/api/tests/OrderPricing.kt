package api.tests

import api.model.Order

object OrderPricing {

    const val DEFAULT_UNIT_PRICE_CENTS = 1000

    fun lineTotalCents(quantity: Int, unitPriceCents: Int = DEFAULT_UNIT_PRICE_CENTS): Int {
        require(quantity > 0) { "quantity must be positive" }
        require(unitPriceCents >= 0) { "unitPriceCents must be non-negative" }
        return quantity * unitPriceCents
    }

    fun lineTotalCents(order: Order, unitPriceCents: Int = DEFAULT_UNIT_PRICE_CENTS): Int? {
        val quantity = order.quantity ?: return null
        if (quantity <= 0) return null
        return lineTotalCents(quantity, unitPriceCents)
    }

    fun isWithinBudget(order: Order, budgetCents: Int, unitPriceCents: Int = DEFAULT_UNIT_PRICE_CENTS): Boolean {
        val total = lineTotalCents(order, unitPriceCents) ?: return false
        return total <= budgetCents
    }
}
