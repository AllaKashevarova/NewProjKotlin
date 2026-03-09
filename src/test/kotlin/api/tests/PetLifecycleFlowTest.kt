package api.tests

import api.client.PetApiClient
import io.ktor.client.plugins.ClientRequestException
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNotNull

class PetLifecycleFlowTest {

    private val client = PetApiClient()

    @Test
    fun `should create fetch and delete pet`() = runBlocking {
        val petRequest = PetTestDataFactory.newAvailablePet()

        val createdPet = client.addPet(petRequest)
        assertNotNull(createdPet.id, "Created pet should have an id")
        assertEquals(petRequest.id, createdPet.id, "Created pet id should match requested id")
        assertEquals(petRequest.name, createdPet.name, "Created pet name should match requested name")
        assertEquals(petRequest.status, createdPet.status, "Created pet status should match requested status")

        val fetchedPet = client.getPetById(createdPet.id!!)
        assertEquals(createdPet.id, fetchedPet.id, "Fetched pet id should match created pet id")
        assertEquals(createdPet.name, fetchedPet.name, "Fetched pet name should match created pet name")
        assertEquals(createdPet.status, fetchedPet.status, "Fetched pet status should match created pet status")

        client.deletePet(createdPet.id!!)

        assertFailsWith<ClientRequestException>(
            message = "Expected fetching deleted pet to fail with ClientRequestException",
        ) {
            client.getPetById(createdPet.id!!)
        }
    }
}

