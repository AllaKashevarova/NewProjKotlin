package api.tests

import api.model.User
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class UserDisplayNameFormatterTest {

    private fun user(
        username: String = "jdoe",
        firstName: String? = null,
        lastName: String? = null,
    ): User = User(username = username, firstName = firstName, lastName = lastName)

    @Test
    fun `should combine first and last name`() {
        val u = user(firstName = "Jane", lastName = "Doe")
        assertEquals("Jane Doe", UserDisplayNameFormatter.displayName(u))
    }

    @Test
    fun `should use first name alone when last name is missing`() {
        assertEquals("Jane", UserDisplayNameFormatter.displayName(user(firstName = "Jane")))
    }

    @Test
    fun `should use last name alone when first name is missing`() {
        assertEquals("Doe", UserDisplayNameFormatter.displayName(user(lastName = "Doe")))
    }

    @Test
    fun `should fall back to username when both names are missing`() {
        assertEquals("jdoe", UserDisplayNameFormatter.displayName(user()))
    }

    @Test
    fun `should treat blank names as missing and trim whitespace`() {
        val u = user(firstName = "  Jane  ", lastName = "   ")
        assertEquals("Jane", UserDisplayNameFormatter.displayName(u))
    }

    @Test
    fun `initials should use first letters of both names uppercased`() {
        val u = user(firstName = "jane", lastName = "doe")
        assertEquals("JD", UserDisplayNameFormatter.initials(u))
    }

    @Test
    fun `initials should use single name when only one is present`() {
        assertEquals("J", UserDisplayNameFormatter.initials(user(firstName = "jane")))
        assertEquals("D", UserDisplayNameFormatter.initials(user(lastName = "doe")))
    }

    @Test
    fun `initials should fall back to first letter of username`() {
        assertEquals("J", UserDisplayNameFormatter.initials(user(username = "jdoe")))
    }

    @Test
    fun `initials should return empty string for blank username without names`() {
        assertEquals("", UserDisplayNameFormatter.initials(user(username = "   ")))
    }
}
