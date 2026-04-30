package api.tests

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.test.assertTrue

class TestDataFactoryTest {

    @Test
    fun `should generate unique usernames with provided prefix`() {
        val usernameOne = TestDataFactory.uniqueUsername("autotest")
        val usernameTwo = TestDataFactory.uniqueUsername("autotest")

        assertTrue(usernameOne.startsWith("autotest_"), "Expected username to include custom prefix")
        assertTrue(usernameTwo.startsWith("autotest_"), "Expected username to include custom prefix")
        assertNotEquals(usernameOne, usernameTwo, "Expected generated usernames to be unique")
    }

    @Test
    fun `should create valid default user payload`() {
        val user = TestDataFactory.newUser()

        assertTrue(user.username.isNotBlank(), "Expected username to be non-empty")
        assertEquals("Auto", user.firstName, "Expected default firstName")
        assertEquals("Test", user.lastName, "Expected default lastName")
        assertTrue(user.email?.contains("@") == true, "Expected email to look valid")
        assertEquals("pass1234", user.password, "Expected default password")
    }
}

