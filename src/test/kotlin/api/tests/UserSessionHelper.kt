package api.tests

import api.client.UserApiClient

object UserSessionHelper {

    fun tokenFromLoginResponse(loginResponse: String): String =
        SessionResponseParser.extractSessionToken(loginResponse)

    suspend fun loginAndGetToken(
        client: UserApiClient,
        username: String,
        password: String,
    ): String {
        val response = client.login(username, password)
        return tokenFromLoginResponse(response)
    }
}
