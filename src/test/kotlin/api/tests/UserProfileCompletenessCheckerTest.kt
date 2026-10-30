package api.tests

import api.model.User
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class UserProfileCompletenessCheckerTest {

    @Test
    fun `fully populated user should be complete`() {
        val result = UserProfileCompletenessChecker.check(TestDataFactory.newUser("complete_user"))

        assertTrue(result.isComplete)
        assertEquals(100, result.completionPercent)
        assertTrue(result.missingFields.isEmpty())
        assertTrue(result.invalidFields.isEmpty())
    }

    @Test
    fun `minimal user should miss every optional field`() {
        val result = UserProfileCompletenessChecker.check(UserTestDataFactory.newMinimalUser("minimal_user"))

        assertFalse(result.isComplete)
        assertEquals(UserProfileField.entries.toSet(), result.missingFields)
        assertEquals(0, result.completionPercent)
    }

    @Test
    fun `blank strings count as missing`() {
        val user = TestDataFactory.newUser("blank_user").copy(firstName = "  ", phone = "")

        val result = UserProfileCompletenessChecker.check(user)

        assertEquals(setOf(UserProfileField.FIRST_NAME, UserProfileField.PHONE), result.missingFields)
    }

    @Test
    fun `malformed email and phone should be reported as invalid`() {
        val user = TestDataFactory.newUser("invalid_user").copy(email = "not-an-email", phone = "12ab")

        val result = UserProfileCompletenessChecker.check(user)

        assertEquals(setOf(UserProfileField.EMAIL, UserProfileField.PHONE), result.invalidFields)
        assertTrue(result.missingFields.isEmpty())
        assertEquals(60, result.completionPercent)
    }

    @Test
    fun `short password should be reported as invalid`() {
        val user = TestDataFactory.newUser("weak_user").copy(password = "short")

        val result = UserProfileCompletenessChecker.check(user)

        assertEquals(setOf(UserProfileField.PASSWORD), result.invalidFields)
    }

    @Test
    fun `password at minimum length should be accepted`() {
        val password = "x".repeat(UserProfileCompletenessChecker.MIN_PASSWORD_LENGTH)
        val user = TestDataFactory.newUser("edge_user").copy(password = password)

        assertTrue(UserProfileCompletenessChecker.check(user).isComplete)
    }

    @Test
    fun `should list only incomplete users`() {
        val complete = TestDataFactory.newUser("ok_user")
        val incomplete = UserTestDataFactory.newMinimalUser("bad_user")

        val result = UserProfileCompletenessChecker.incompleteUsers(listOf(complete, incomplete))

        assertEquals(listOf(incomplete), result)
    }

    @Test
    fun `should average completion percent across users`() {
        val users: List<User> = listOf(
            TestDataFactory.newUser("avg_full"),
            UserTestDataFactory.newMinimalUser("avg_empty"),
        )

        assertEquals(50, UserProfileCompletenessChecker.averageCompletionPercent(users))
    }

    @Test
    fun `average completion percent should be zero for empty list`() {
        assertEquals(0, UserProfileCompletenessChecker.averageCompletionPercent(emptyList()))
    }
}
