package api.tests

import api.client.UserApiClient
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class UserProfileUpdateFlowTest {

    private val client = UserApiClient()

    @Test
    fun `should update user profile and allow login with updated password`() = runBlocking {
        val createdUser = UserTestDataFactory.newUpdatableUser()
        val updatedUser = UserTestDataFactory.updatedUserProfile(createdUser)
        var created = false

        try {
            val createResponse = client.createUser(createdUser)
            assertEquals(200, createResponse.status.value, "Expected successful user creation")
            created = true

            val updateResponse = client.updateUser(createdUser.username, updatedUser)
            assertEquals(200, updateResponse.status.value, "Expected successful user update")

            val fetchedUser = client.getUser(createdUser.username)
            ApiAssertions.assertUserCoreFields(
                expected = updatedUser,
                actual = fetchedUser,
                context = "Updated fetched user",
            )

            val loginResponse = client.login(
                username = updatedUser.username,
                password = updatedUser.password ?: "",
            )
            val sessionToken = SessionResponseParser.extractSessionToken(loginResponse)
            assertTrue(
                loginResponse.contains("logged in user session"),
                "Expected login response to contain session information",
            )
            assertTrue(sessionToken.isNotBlank(), "Expected non-empty session token after successful login")
        } finally {
            if (created) {
                runCatching { client.deleteUser(createdUser.username) }
            }
        }
    }
}

