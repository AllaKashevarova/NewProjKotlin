package api.tests

import org.junit.jupiter.api.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class UserCredentialsValidatorTest {

    @Test
    fun `should accept valid test credentials`() {
        val user = TestDataFactory.newUser("qa_user_01")
        assertTrue(UserCredentialsValidator.isValidUsername(user.username))
        assertTrue(UserCredentialsValidator.isValidPassword(user.password!!))
        assertTrue(UserCredentialsValidator.isValidCredentials(user.username, user.password!!))
    }

    @Test
    fun `should reject invalid username formats`() {
        assertFalse(UserCredentialsValidator.isValidUsername("ab"))
        assertFalse(UserCredentialsValidator.isValidUsername("_starts_with_underscore"))
        assertFalse(UserCredentialsValidator.isValidUsername("has space"))
    }

    @Test
    fun `should reject weak passwords`() {
        assertFalse(UserCredentialsValidator.isValidPassword("123"))
        assertFalse(UserCredentialsValidator.isValidPassword(""))
    }
}
