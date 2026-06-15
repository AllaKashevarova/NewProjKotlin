package api.tests

import api.client.UserApiClient
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import kotlin.test.assertTrue

class UserCreationHttpStatusFlowTest {

    private val client = UserApiClient()

    @Test
    fun `should create user and assert successful http status`() = runBlocking {
        val username = TestDataFactory.uniqueUsername("http_status")
        val user = TestDataFactory.newUser(username)
        assertTrue(UserCredentialsValidator.isValidCredentials(username, user.password!!))

        val createResponse = client.createUser(user)
        HttpStatusAssertions.assertSuccess(createResponse, "User creation")

        try {
            val fetchedUser = client.getUser(username)
            ApiAssertions.assertUserCoreFields(user, fetchedUser, "Fetched user")
        } finally {
            runCatching { client.deleteUser(username) }
        }
    }
}
