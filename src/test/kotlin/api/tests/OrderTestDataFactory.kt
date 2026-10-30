package api.tests

import api.model.Order

object OrderTestDataFactory {

    fun newPlacedOrder(
        petId: Long = 1L,
        quantity: Int = 1,
        complete: Boolean = false,
    ): Order =
        Order(
            id = null,
            petId = petId,
            quantity = quantity,
            status = "placed",
            complete = complete,
        )

    fun newOrderWithStatus(
        status: String,
        petId: Long = 1L,
        quantity: Int = 1,
    ): Order =
        Order(
            id = null,
            petId = petId,
            quantity = quantity,
            status = status,
            complete = OrderStatusRules.isTerminal(status),
        )

    fun newApprovedOrder(
        petId: Long = 1L,
        quantity: Int = 1,
    ): Order = newOrderWithStatus(OrderStatusRules.APPROVED, petId, quantity)

    fun newOrderBatch(statuses: List<String>, petId: Long = 1L): List<Order> =
        statuses.mapIndexed { index, status ->
            newOrderWithStatus(status = status, petId = petId, quantity = index + 1)
        }

    fun newCompletedOrder(
        petId: Long = 1L,
        quantity: Int = 1,
    ): Order =
        Order(
            id = null,
            petId = petId,
            quantity = quantity,
            status = "delivered",
            complete = true,
        )
}

