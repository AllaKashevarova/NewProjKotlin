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

