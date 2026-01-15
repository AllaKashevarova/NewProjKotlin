package api.tests

import api.client.UserApiClient
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class UserApiTest {

    private val client = UserApiClient()

    @Test
    fun `should create and fetch user`() = runBlocking {
        val newUser = TestDataFactory.newUser()

        val createResponse = client.createUser(newUser)
        assertEquals(200, createResponse.status.value, "Expected successful user creation")

        val fetchedUser = client.getUser(newUser.username)
        assertEquals(newUser.username, fetchedUser.username, "Fetched user should match created user")
    }
}

