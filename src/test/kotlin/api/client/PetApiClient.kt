@file:Suppress("ktlint:standard:no-wildcard-imports")

package api.client

import api.config.ApiConfig
import api.model.Pet
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

class PetApiClient {

    private val baseUrl = ""
    private val test = ""

    private val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json(Json { ignoreUnknownKeys = true })
        }
    }

    suspend fun getAvailablePets(): List<Pet> {
        return client.get("${ApiConfig.BASE_URL}${ApiConfig.PETS_BY_STATUS}") {
            url {
                parameters.append("status", "available")
            }
            accept(ContentType.Application.Json)
        }.body()
    }
}
