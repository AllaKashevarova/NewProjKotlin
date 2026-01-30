package api.tests

import api.client.StoreApiClient
import api.model.Order
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import kotlin.test.assertFailsWith
import kotlin.test.assertNotNull

class StoreDeleteOrderTest {

    private val client = StoreApiClient()

    @Test
    fun `should delete order and fail to fetch afterwards`() = runBlocking {
        val orderRequest =
            Order(
                petId = 1L,
                quantity = 1,
                status = "placed",
                complete = false,
            )

        // Place a new order
        val createdOrder = client.placeOrder(orderRequest)
        assertNotNull(createdOrder.id, "Created order should have an id")

        val orderId = createdOrder.id!!

        // Delete the order
        client.deleteOrder(orderId)

        // Trying to fetch a deleted order should result in an error from the API
        assertFailsWith<Exception>("Expected fetching deleted order to fail") {
            runBlocking {
                client.getOrderById(orderId)
            }
        }
    }
}

