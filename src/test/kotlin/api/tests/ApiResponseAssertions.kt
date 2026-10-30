package api.tests

import api.model.ApiResponse
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

object ApiResponseAssertions {

    const val SUCCESS_CODE = 200

    fun isSuccess(response: ApiResponse): Boolean = response.code == SUCCESS_CODE

    fun assertSuccess(response: ApiResponse, context: String) {
        assertCode(response, SUCCESS_CODE, context)
    }

    fun assertCode(response: ApiResponse, expectedCode: Int, context: String) {
        assertEquals(expectedCode, response.code, "$context: response code should match")
    }

    fun assertType(response: ApiResponse, expectedType: String, context: String) {
        assertEquals(expectedType, response.type, "$context: response type should match")
    }

    fun assertMessageContains(response: ApiResponse, fragment: String, context: String) {
        val message = response.message
        assertNotNull(message, "$context: response message should be present")
        assertTrue(
            message.contains(fragment, ignoreCase = true),
            "$context: response message '$message' should contain '$fragment'",
        )
    }

    fun assertMessageEquals(response: ApiResponse, expected: String, context: String) {
        assertEquals(expected, response.message, "$context: response message should match")
    }
}
