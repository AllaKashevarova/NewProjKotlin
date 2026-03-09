package api.client

import api.config.ApiConfig
import api.model.Pet
import api.model.PetStatus
import io.ktor.client.call.body
import io.ktor.client.request.*
import io.ktor.http.*

class PetApiClient : BaseApiClient() {

    suspend fun getAvailablePets(): List<Pet> =
        client.get("${ApiConfig.BASE_URL}${ApiConfig.PET_FIND_BY_STATUS}") {
            parameter("status", PetStatus.AVAILABLE.value)
            accept(ContentType.Application.Json)
        }.body()

    suspend fun addPet(pet: Pet): Pet =
        client.post("${ApiConfig.BASE_URL}${ApiConfig.PET}") {
            contentType(ContentType.Application.Json)
            setBody(pet)
        }.body()

    suspend fun getPetById(petId: Long): Pet =
        client.get("${ApiConfig.BASE_URL}${ApiConfig.PET}/$petId") {
            accept(ContentType.Application.Json)
        }.body()

    suspend fun deletePet(petId: Long) {
        client.delete("${ApiConfig.BASE_URL}${ApiConfig.PET}/$petId") {
            accept(ContentType.Application.Json)
        }
    }
}
