package api.tests

import api.client.StoreApiClient
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class StoreOrderStatusFlowTest {

    private val client = StoreApiClient()

    @Test
    fun `should create order and verify status and completion flags`() = runBlocking {
        val orderRequest = OrderTestDataFactory.newPlacedOrder(petId = 1L, quantity = 2)

        val createdOrder = client.placeOrder(orderRequest)
        assertNotNull(createdOrder.id, "Created order should have an id")
        assertEquals("placed", createdOrder.status, "Expected created order status to be 'placed'")
        assertEquals(false, createdOrder.complete, "Expected created order to be not complete")

        val fetchedOrder = client.getOrderById(createdOrder.id!!)
        assertEquals(createdOrder.id, fetchedOrder.id, "Fetched order id should match created order id")
        assertEquals("placed", fetchedOrder.status, "Expected fetched order status to be 'placed'")
        assertEquals(false, fetchedOrder.complete, "Expected fetched order to be not complete")
    }
}

