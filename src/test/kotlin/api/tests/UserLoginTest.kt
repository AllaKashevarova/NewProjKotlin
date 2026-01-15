package api.tests

import api.client.UserApiClient
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import kotlin.test.assertTrue

class UserLoginTest {

    private val client = UserApiClient()

    @Test
    fun `should login and return session token`() = runBlocking {
        // Using the default demo credentials from the Petstore API documentation
        val username = "user1"
        val password = "password1"

        val loginResponse = client.login(username, password)

        // The Petstore API typically returns something like "logged in user session:XXXXXXXX"
        assertTrue(
            loginResponse.contains("logged in user session"),
            "Expected login response to contain session information",
        )
    }
}

