package api.tests

import api.client.StoreApiClient
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class StoreOrderQuantityFlowTest {

    private val client = StoreApiClient()

    @Test
    fun `should place order only when quantity passes validation`() = runBlocking {
        val quantity = OrderQuantityValidator.requireValid(3)
        val orderRequest = OrderTestDataFactory.newPlacedOrder(petId = 1L, quantity = quantity)
        assertTrue(OrderPricing.isWithinBudget(orderRequest, budgetCents = 10_000))

        val createdOrder = client.placeOrder(orderRequest)
        assertNotNull(createdOrder.id, "Created order should have an id")
        assertTrue(OrderStatusRules.isValid(createdOrder.status))

        runCatching { client.deleteOrder(createdOrder.id!!) }
    }
}
