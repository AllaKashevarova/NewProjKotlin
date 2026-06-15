package api.tests

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class OrderQuantityValidatorTest {

    @Test
    fun `should accept quantity within allowed range`() {
        assertTrue(OrderQuantityValidator.isValid(1))
        assertTrue(OrderQuantityValidator.isValid(50))
        assertEquals(10, OrderQuantityValidator.requireValid(10))
    }

    @Test
    fun `should reject zero negative or excessive quantity`() {
        assertFalse(OrderQuantityValidator.isValid(0))
        assertFalse(OrderQuantityValidator.isValid(-1))
        assertFalse(OrderQuantityValidator.isValid(101))
    }
}
