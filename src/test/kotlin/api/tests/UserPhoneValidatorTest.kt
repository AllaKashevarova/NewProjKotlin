package api.tests

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertTrue

class UserPhoneValidatorTest {

    @Test
    fun `should accept plain digit phone numbers`() {
        assertTrue(UserPhoneValidator.isValid("5551234567"))
    }

    @Test
    fun `should accept international format with leading plus`() {
        assertTrue(UserPhoneValidator.isValid("+375291234567"))
    }

    @Test
    fun `should accept common separators`() {
        assertTrue(UserPhoneValidator.isValid("+1 (555) 123-4567"))
        assertTrue(UserPhoneValidator.isValid("555.123.4567"))
    }

    @Test
    fun `should reject null blank and empty values`() {
        assertFalse(UserPhoneValidator.isValid(null))
        assertFalse(UserPhoneValidator.isValid(""))
        assertFalse(UserPhoneValidator.isValid("   "))
    }

    @Test
    fun `should reject letters and invalid characters`() {
        assertFalse(UserPhoneValidator.isValid("555-CALL-NOW"))
        assertFalse(UserPhoneValidator.isValid("5551234567#"))
    }

    @Test
    fun `should reject plus sign not at the start`() {
        assertFalse(UserPhoneValidator.isValid("555+1234567"))
    }

    @Test
    fun `should reject too short and too long numbers`() {
        assertFalse(UserPhoneValidator.isValid("123456"))
        assertFalse(UserPhoneValidator.isValid("1234567890123456"))
    }

    @Test
    fun `should accept boundary lengths`() {
        assertTrue(UserPhoneValidator.isValid("1234567"))
        assertTrue(UserPhoneValidator.isValid("123456789012345"))
    }

    @Test
    fun `normalize should strip separators and keep leading plus`() {
        assertEquals("+15551234567", UserPhoneValidator.normalize("+1 (555) 123-4567"))
        assertEquals("5551234567", UserPhoneValidator.normalize("555.123.4567"))
    }

    @Test
    fun `normalize should trim surrounding whitespace`() {
        assertEquals("+375291234567", UserPhoneValidator.normalize("  +375 29 123 45 67  "))
    }

    @Test
    fun `normalize should return null when no digits are present`() {
        assertNull(UserPhoneValidator.normalize(null))
        assertNull(UserPhoneValidator.normalize("   "))
        assertNull(UserPhoneValidator.normalize("+"))
    }
}
