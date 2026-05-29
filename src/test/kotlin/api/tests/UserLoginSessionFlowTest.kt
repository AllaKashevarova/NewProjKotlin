package api.tests

import api.client.UserApiClient
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class UserLoginSessionFlowTest {

    private val client = UserApiClient()

    @Test
    fun `should create user login and extract session token`() = runBlocking {
        val username = TestDataFactory.uniqueUsername("session")
        val user = TestDataFactory.newUser(username)
        assertTrue(UserCredentialsValidator.isValidCredentials(username, user.password!!))

        val createResponse = client.createUser(user)
        assertEquals(200, createResponse.status.value, "Expected successful user creation")

        try {
            val token = UserSessionHelper.loginAndGetToken(
                client = client,
                username = username,
                password = user.password!!,
            )
            assertTrue(token.isNotEmpty(), "Expected non-empty session token after login")

            client.logout()
        } finally {
            runCatching { client.deleteUser(username) }
        }
    }
}
