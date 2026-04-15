package api.tests

import api.model.Order
import api.model.Pet
import api.model.User
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

    fun assertUserCoreFields(expected: User, actual: User, context: String) {
        assertEquals(expected.username, actual.username, "$context: username should match")
        assertEquals(expected.firstName, actual.firstName, "$context: firstName should match")
        assertEquals(expected.lastName, actual.lastName, "$context: lastName should match")
        assertEquals(expected.email, actual.email, "$context: email should match")
        assertEquals(expected.phone, actual.phone, "$context: phone should match")
        assertEquals(expected.userStatus, actual.userStatus, "$context: userStatus should match")
    }
}

