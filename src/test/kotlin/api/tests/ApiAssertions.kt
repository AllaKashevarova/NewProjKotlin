package api.tests

import api.model.Order
import api.model.Pet
import kotlin.test.assertEquals

object ApiAssertions {

    fun assertPetCoreFields(expected: Pet, actual: Pet, context: String) {
        assertEquals(expected.id, actual.id, "$context: pet id should match")
        assertEquals(expected.name, actual.name, "$context: pet name should match")
        assertEquals(expected.status, actual.status, "$context: pet status should match")
    }

    fun assertOrderCoreFields(expected: Order, actual: Order, context: String) {
        assertEquals(expected.id, actual.id, "$context: order id should match")
        assertEquals(expected.petId, actual.petId, "$context: order petId should match")
        assertEquals(expected.quantity, actual.quantity, "$context: order quantity should match")
        assertEquals(expected.status, actual.status, "$context: order status should match")
        assertEquals(expected.complete, actual.complete, "$context: order complete flag should match")
    }
}

