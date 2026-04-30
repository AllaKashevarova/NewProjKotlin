package api.tests

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

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
}

