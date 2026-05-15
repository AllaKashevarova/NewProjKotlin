package api.tests

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class PetNameValidatorTest {

    @Test
    fun `should accept alphanumeric names with separators`() {
        assertTrue(PetNameValidator.isValid("auto-pet_01"))
        assertTrue(PetNameValidator.isValid("Lucky Dog"))
    }

    @Test
    fun `should reject blank or too long names`() {
        assertFalse(PetNameValidator.isValid(""))
        assertFalse(PetNameValidator.isValid("   "))
        assertFalse(PetNameValidator.isValid("a".repeat(PetNameValidator.MAX_LENGTH + 1)))
    }

    @Test
    fun `should reject names with unsupported characters`() {
        assertFalse(PetNameValidator.isValid("@invalid"))
        assertFalse(PetNameValidator.isValid(" leading"))
    }

    @Test
    fun `should sanitize unsafe input to a usable pet name`() {
        val sanitized = PetNameValidator.sanitize("  @bad#name!!!  ")
        assertTrue(PetNameValidator.isValid(sanitized))
        assertEquals("badname", sanitized)
    }
}
