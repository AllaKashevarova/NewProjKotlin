package api.tests

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class SessionResponseParserTest {

    @Test
    fun `should extract session token from standard login response`() {
        val response = "logged in user session:1234567890"
        val token = SessionResponseParser.extractSessionToken(response)
        assertEquals("1234567890", token)
    }

    @Test
    fun `should extract token when marker uses different case`() {
        val response = "logged in user Session:ABC_def-123"
        val token = SessionResponseParser.extractSessionToken(response)
        assertEquals("ABC_def-123", token)
    }

    @Test
    fun `should return empty token when session marker is missing`() {
        val response = "login ok but no token available"
        val token = SessionResponseParser.extractSessionToken(response)
        assertEquals("", token)
    }

    @Test
    fun `should trim token and ignore trailing punctuation`() {
        val response = "logged in user session:  token-12345!!!"
        val token = SessionResponseParser.extractSessionToken(response)
        assertEquals("token-12345", token)
    }
}

