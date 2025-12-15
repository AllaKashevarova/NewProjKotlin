package api.client

import api.config.ApiConfig
import api.model.User
import io.ktor.client.call.body
import io.ktor.client.request.*
import io.ktor.client.statement.HttpResponse
import io.ktor.http.*

class UserApiClient : BaseApiClient() {

    suspend fun createUser(user: User): HttpResponse =
        client.post("${ApiConfig.BASE_URL}${ApiConfig.USER}") {
            contentType(ContentType.Application.Json)
            setBody(user)
        }

    suspend fun getUser(username: String): User =
        client.get("${ApiConfig.BASE_URL}${ApiConfig.USER}/$username") {
            accept(ContentType.Application.Json)
        }.body()

    suspend fun deleteUser(username: String): HttpResponse =
        client.delete("${ApiConfig.BASE_URL}${ApiConfig.USER}/$username")

    suspend fun login(username: String, password: String): String =
        client.get("${ApiConfig.BASE_URL}${ApiConfig.USER_LOGIN}") {
            url {
                parameters.append("username", username)
                parameters.append("password", password)
            }
            accept(ContentType.Application.Json)
        }.body()

    suspend fun logout(): HttpResponse =
        client.get("${ApiConfig.BASE_URL}${ApiConfig.USER_LOGOUT}")
}
