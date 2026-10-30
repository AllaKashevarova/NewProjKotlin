package api.tests

import api.model.ApiResponse
import org.junit.jupiter.api.Test
import kotlin.test.assertFailsWith
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class ApiResponseAssertionsTest {

    private val ok = ApiResponse(code = 200, type = "unknown", message = "User 42 logged in")

    @Test
    fun `should recognize success response`() {
        assertTrue(ApiResponseAssertions.isSuccess(ok))
        assertFalse(ApiResponseAssertions.isSuccess(ApiResponse(code = 404)))
        assertFalse(ApiResponseAssertions.isSuccess(ApiResponse()))
    }

    @Test
    fun `assert success should pass for 200`() {
        ApiResponseAssertions.assertSuccess(ok, "login")
    }

    @Test
    fun `assert success should fail for other codes and include context`() {
        val error = assertFailsWith<AssertionError> {
            ApiResponseAssertions.assertSuccess(ApiResponse(code = 500), "login")
        }
        assertTrue(error.message.orEmpty().contains("login"))
    }

    @Test
    fun `assert code should compare against expected code`() {
        ApiResponseAssertions.assertCode(ApiResponse(code = 1), 1, "pet lookup")
        assertFailsWith<AssertionError> {
            ApiResponseAssertions.assertCode(ApiResponse(code = 1), 2, "pet lookup")
        }
    }

    @Test
    fun `assert type should compare against expected type`() {
        ApiResponseAssertions.assertType(ok, "unknown", "login")
        assertFailsWith<AssertionError> { ApiResponseAssertions.assertType(ok, "error", "login") }
    }

    @Test
    fun `assert message contains should be case-insensitive`() {
        ApiResponseAssertions.assertMessageContains(ok, "LOGGED IN", "login")
    }

    @Test
    fun `assert message contains should fail when fragment is absent`() {
        assertFailsWith<AssertionError> {
            ApiResponseAssertions.assertMessageContains(ok, "logged out", "login")
        }
    }

    @Test
    fun `assert message contains should fail when message is missing`() {
        assertFailsWith<AssertionError> {
            ApiResponseAssertions.assertMessageContains(ApiResponse(code = 200), "anything", "login")
        }
    }

    @Test
    fun `assert message equals should require exact match`() {
        ApiResponseAssertions.assertMessageEquals(ok, "User 42 logged in", "login")
        assertFailsWith<AssertionError> {
            ApiResponseAssertions.assertMessageEquals(ok, "user 42 logged in", "login")
        }
    }
}
