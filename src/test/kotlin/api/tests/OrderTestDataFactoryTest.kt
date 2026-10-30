package api.tests

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlin.test.assertTrue

class OrderTestDataFactoryTest {

    @Test
    fun `should create placed order with expected defaults`() {
        val order = OrderTestDataFactory.newPlacedOrder()

        assertNull(order.id, "Expected id to be null before API creation")
        assertEquals(1L, order.petId, "Expected default pet id")
        assertEquals(1, order.quantity, "Expected default quantity")
        assertEquals("placed", order.status, "Expected placed status")
        assertEquals(false, order.complete, "Expected not completed by default")
    }

    @Test
    fun `should create completed order with delivered status`() {
        val order = OrderTestDataFactory.newCompletedOrder(petId = 11L, quantity = 4)

        assertNull(order.id, "Expected id to be null before API creation")
        assertEquals(11L, order.petId, "Expected provided pet id")
        assertEquals(4, order.quantity, "Expected provided quantity")
        assertEquals("delivered", order.status, "Expected delivered status")
        assertTrue(order.complete == true, "Expected completed order")
    }

    @Test
    fun `should create approved order that is not complete`() {
        val order = OrderTestDataFactory.newApprovedOrder(petId = 7L, quantity = 2)

        assertEquals("approved", order.status, "Expected approved status")
        assertEquals(7L, order.petId, "Expected provided pet id")
        assertEquals(2, order.quantity, "Expected provided quantity")
        assertEquals(false, order.complete, "Expected approved order to be incomplete")
    }

    @Test
    fun `should mark only delivered status as complete`() {
        assertEquals(true, OrderTestDataFactory.newOrderWithStatus("delivered").complete)
        assertEquals(false, OrderTestDataFactory.newOrderWithStatus("placed").complete)
    }

    @Test
    fun `should build batch preserving status order with increasing quantities`() {
        val batch = OrderTestDataFactory.newOrderBatch(listOf("placed", "approved", "delivered"), petId = 9L)

        assertEquals(listOf("placed", "approved", "delivered"), batch.map { it.status })
        assertEquals(listOf(1, 2, 3), batch.map { it.quantity })
        assertTrue(batch.all { it.petId == 9L }, "Expected all orders to share the pet id")
    }

    @Test
    fun `should build empty batch for no statuses`() {
        assertTrue(OrderTestDataFactory.newOrderBatch(emptyList()).isEmpty())
    }
}

