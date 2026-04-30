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
}

