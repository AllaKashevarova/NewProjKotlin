package api.tests

import org.junit.jupiter.api.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class OrderStatusRulesTest {

    @Test
    fun `should validate known order statuses`() {
        assertTrue(OrderStatusRules.isValid("placed"))
        assertTrue(OrderStatusRules.isValid("DELIVERED"))
        assertFalse(OrderStatusRules.isValid("cancelled"))
        assertFalse(OrderStatusRules.isValid(null))
    }

    @Test
    fun `should identify terminal status`() {
        assertTrue(OrderStatusRules.isTerminal("delivered"))
        assertFalse(OrderStatusRules.isTerminal("placed"))
    }

    @Test
    fun `should allow only valid status transitions`() {
        assertTrue(OrderStatusRules.canTransition("placed", "approved"))
        assertTrue(OrderStatusRules.canTransition("approved", "delivered"))
        assertFalse(OrderStatusRules.canTransition("delivered", "placed"))
        assertFalse(OrderStatusRules.canTransition("placed", "unknown"))
    }
}
