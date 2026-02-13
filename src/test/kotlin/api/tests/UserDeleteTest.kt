package api.tests

import api.client.UserApiClient
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import io.ktor.client.plugins.ClientRequestException
import kotlin.test.assertFailsWith

class UserDeleteTest {

    private val client = UserApiClient()

    @Test
    fun `should delete user and fail to fetch afterwards`() = runBlocking {
        val username = TestDataFactory.uniqueUsername()
        val user = TestDataFactory.newUser(username)

        // Create a new user
        client.createUser(user)

        // Delete the user
        client.deleteUser(username)

        // Fetching deleted user should result in 404 -> ClientRequestException
        assertFailsWith<ClientRequestException>(
            message = "Expected fetching deleted user to fail with ClientRequestException",
        ) {
            client.getUser(username)
        }
    }
}

