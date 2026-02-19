package api.tests

import api.client.StoreApiClient
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class StoreApiOrderTest {

    private val client = StoreApiClient()

    @Test
    fun `should place and fetch order`() = runBlocking {
        val orderRequest = OrderTestDataFactory.newPlacedOrder(petId = 1L, quantity = 1)

        val createdOrder = client.placeOrder(orderRequest)
        assertNotNull(createdOrder.id, "Created order should have an id")

        val fetchedOrder = client.getOrderById(createdOrder.id!!)
        assertEquals(createdOrder.id, fetchedOrder.id, "Fetched order id should match created order id")
    }
}

