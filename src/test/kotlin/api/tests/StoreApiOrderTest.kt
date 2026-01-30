package api.tests

import api.client.StoreApiClient
import api.model.Order
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class StoreApiOrderTest {

    private val client = StoreApiClient()

    @Test
    fun `should place and fetch order`() = runBlocking {
        val orderRequest =
            Order(
                petId = 1L,
                quantity = 1,
                status = "placed",
                complete = false,
            )

        val createdOrder = client.placeOrder(orderRequest)
        assertNotNull(createdOrder.id, "Created order should have an id")

        val fetchedOrder = client.getOrderById(createdOrder.id!!)
        assertEquals(createdOrder.id, fetchedOrder.id, "Fetched order id should match created order id")
    }
}

