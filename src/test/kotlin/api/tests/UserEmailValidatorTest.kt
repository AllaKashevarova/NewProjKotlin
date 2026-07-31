package api.tests

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class UserEmailValidatorTest {

    @Test
    fun `should accept well-formed addresses`() {
        assertTrue(UserEmailValidator.isValid("auto.user+01@example.com"))
        assertTrue(UserEmailValidator.isValid("Test_User@sub.domain.co"))
    }

    @Test
    fun `should reject blank or overly long addresses`() {
        assertFalse(UserEmailValidator.isValid(""))
        assertFalse(UserEmailValidator.isValid("   "))
        val tooLong = "a".repeat(UserEmailValidator.MAX_LENGTH) + "@example.com"
        assertFalse(UserEmailValidator.isValid(tooLong))
    }

    @Test
    fun `should reject malformed addresses`() {
        assertFalse(UserEmailValidator.isValid("no-at-sign.com"))
        assertFalse(UserEmailValidator.isValid("two@@example.com"))
        assertFalse(UserEmailValidator.isValid("missing@tld"))
        assertFalse(UserEmailValidator.isValid("trailing@example.c"))
    }

    @Test
    fun `should normalize by trimming and lowercasing`() {
        assertEquals("user@example.com", UserEmailValidator.normalize("  User@Example.COM  "))
    }
}
