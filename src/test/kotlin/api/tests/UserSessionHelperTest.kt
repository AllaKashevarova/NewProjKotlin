package api.tests

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class UserSessionHelperTest {

    @Test
    fun `should extract session token from login response text`() {
        val response = "logged in user session:99887766"
        val token = UserSessionHelper.tokenFromLoginResponse(response)
        assertEquals("99887766", token)
    }

    @Test
    fun `should return empty token when login response has no session`() {
        val token = UserSessionHelper.tokenFromLoginResponse("invalid login response")
        assertTrue(token.isEmpty())
    }
}
