package api.tests

object SessionResponseParser {

    /**
     * Petstore login response usually looks like:
     * "logged in user session:1234567890"
     */
    fun extractSessionToken(loginResponse: String): String {
        val marker = "session:"
        val markerIndex = loginResponse.indexOf(marker, ignoreCase = true)
        if (markerIndex == -1) return ""

        val rawToken = loginResponse.substring(markerIndex + marker.length).trim()
        if (rawToken.isEmpty()) return ""

        // Keep only token-safe chars to avoid trailing punctuation from message text.
        return rawToken.takeWhile { it.isLetterOrDigit() || it == '-' || it == '_' }
    }
}

