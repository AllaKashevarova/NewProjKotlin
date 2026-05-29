package api.tests

import api.client.StoreApiClient
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class StoreOrderBudgetFlowTest {

    private val client = StoreApiClient()

    @Test
    fun `should place order when within configured budget`() = runBlocking {
        val orderRequest = OrderTestDataFactory.newPlacedOrder(petId = 1L, quantity = 2)
        assertTrue(
            OrderPricing.isWithinBudget(orderRequest, budgetCents = 5000),
            "Expected test order to fit default budget",
        )

        val createdOrder = client.placeOrder(orderRequest)
        assertNotNull(createdOrder.id, "Created order should have an id")

        try {
            assertTrue(OrderStatusRules.isValid(createdOrder.status), "Expected valid order status from API")
        } finally {
            runCatching { client.deleteOrder(createdOrder.id!!) }
        }
    }
}
