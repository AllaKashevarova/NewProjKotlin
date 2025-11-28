package api.client

import api.config.ApiConfig
import api.model.User
import io.ktor.client.call.body
import io.ktor.client.request.*
import io.ktor.http.*

class UserApiClient : BaseApiClient() {

    suspend fun createUser(user: User): User =
        client.post("${ApiConfig.BASE_URL}${ApiConfig.USER}") {
            contentType(ContentType.Application.Json)
            setBody(user)
        }.body()

    suspend fun getUserByUsername(username: String): User =
        client.get("${ApiConfig.BASE_URL}${ApiConfig.USER}/$username") {
            accept(ContentType.Application.Json)
        }.body()

    suspend fun deleteUser(username: String) {
        client.delete("${ApiConfig.BASE_URL}${ApiConfig.USER}/$username") {
            accept(ContentType.Application.Json)
        }
    }

    suspend fun login(username: String, password: String): String =
        client.get("${ApiConfig.BASE_URL}${ApiConfig.USER_LOGIN}") {
            parameter("username", username)
            parameter("password", password)
        }.body()

    suspend fun logout() {
        client.get("${ApiConfig.BASE_URL}${ApiConfig.USER_LOGOUT}")
    }
}
