package api.tests

object SessionResponseParser {

    /**
     * Petstore login response usually looks like:
     * "logged in user session:1234567890"
     */
    fun extractSessionToken(loginResponse: String): String {
        val marker = "session:"
        val markerIndex = loginResponse.indexOf(marker)
        if (markerIndex == -1) return ""
        return loginResponse.substring(markerIndex + marker.length).trim()
    }
}

