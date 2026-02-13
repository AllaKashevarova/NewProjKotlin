package api.tests

import api.client.UserApiClient
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class UserLoginFlowTest {

    private val client = UserApiClient()

    @Test
    fun `should create user then login and logout successfully`() = runBlocking {
        val username = TestDataFactory.uniqueUsername()
        val user = TestDataFactory.newUser(username)

        // Create a new user
        val createResponse = client.createUser(user)
        assertEquals(200, createResponse.status.value, "Expected successful user creation")

        // Login with the newly created user
        val loginResponse = client.login(username, user.password ?: "pass1234")
        assertTrue(
            loginResponse.contains("logged in user session"),
            "Expected login response to contain session information",
        )

        // Logout should complete without throwing an exception
        client.logout()
    }
}

