package api.tests

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.test.assertNull
import kotlin.test.assertTrue

class UserTestDataFactoryTest {

    @Test
    fun `should create updatable user with expected defaults`() {
        val user = UserTestDataFactory.newUpdatableUser("flow_user_1")

        assertEquals("flow_user_1", user.username, "Expected provided username to be preserved")
        assertEquals("Initial", user.firstName, "Expected default initial firstName")
        assertEquals("Profile", user.lastName, "Expected default initial lastName")
        assertEquals("pass1234", user.password, "Expected default initial password")
        assertEquals(1, user.userStatus, "Expected default initial userStatus")
    }

    @Test
    fun `should build updated profile while preserving username`() {
        val original = UserTestDataFactory.newUpdatableUser("flow_user_2")
        val updated = UserTestDataFactory.updatedUserProfile(original)

        assertEquals(original.username, updated.username, "Expected username to remain unchanged")
        assertNotEquals(original.firstName, updated.firstName, "Expected firstName to be updated")
        assertNotEquals(original.email, updated.email, "Expected email to be updated")
        assertNotEquals(original.password, updated.password, "Expected password to be updated")
        assertEquals(2, updated.userStatus, "Expected updated userStatus")
    }

    @Test
    fun `should create minimal user with only a username`() {
        val user = UserTestDataFactory.newMinimalUser("min_user")

        assertEquals("min_user", user.username, "Expected provided username")
        assertNull(user.id, "Expected no id")
        assertNull(user.firstName, "Expected no firstName")
        assertNull(user.email, "Expected no email")
        assertNull(user.password, "Expected no password")
    }

    @Test
    fun `should generate unique username for minimal user by default`() {
        val first = UserTestDataFactory.newMinimalUser()
        val second = UserTestDataFactory.newMinimalUser()

        assertNotEquals(first.username, second.username, "Expected unique default usernames")
    }

    @Test
    fun `should build batch of users with unique usernames`() {
        val users = UserTestDataFactory.newUserBatch(count = 4, prefix = "bulk")

        assertEquals(4, users.size, "Expected requested batch size")
        assertEquals(4, users.map { it.username }.toSet().size, "Expected unique usernames")
        assertTrue(users.all { it.username.startsWith("bulk_") }, "Expected usernames to use the prefix")
    }

    @Test
    fun `should reject negative batch size`() {
        assertThrows<IllegalArgumentException> { UserTestDataFactory.newUserBatch(count = -1) }
    }
}

