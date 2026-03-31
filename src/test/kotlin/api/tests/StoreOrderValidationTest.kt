package api.tests

import api.client.StoreApiClient
import io.ktor.client.plugins.ClientRequestException
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import kotlin.test.assertFailsWith
import kotlin.test.assertNotNull

class StoreOrderValidationTest {

    private val client = StoreApiClient()

    @Test
    fun `should place delivered order and preserve status`() = runBlocking {
        val orderRequest = OrderTestDataFactory.newCompletedOrder(petId = 2L, quantity = 3)
        val createdOrder = client.placeOrder(orderRequest)
        assertNotNull(createdOrder.id, "Created order should have an id")

        ApiAssertions.assertOrderCoreFields(
            expected = orderRequest.copy(id = createdOrder.id),
            actual = createdOrder,
            context = "Created order",
        )

        val fetchedOrder = client.getOrderById(createdOrder.id!!)
        ApiAssertions.assertOrderCoreFields(
            expected = createdOrder,
            actual = fetchedOrder,
            context = "Fetched order",
        )
    }

    @Test
    fun `should fail to fetch unknown order id`() = runBlocking {
        assertFailsWith<ClientRequestException>(
            message = "Expected fetching unknown order id to fail with ClientRequestException",
        ) {
            client.getOrderById(999_999_999)
        }
    }
}

