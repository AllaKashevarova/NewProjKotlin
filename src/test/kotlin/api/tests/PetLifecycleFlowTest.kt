package api.tests

import api.client.PetApiClient
import io.ktor.client.plugins.ClientRequestException
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import kotlin.test.assertFailsWith
import kotlin.test.assertNotNull

class PetLifecycleFlowTest {

    private val client = PetApiClient()

    @Test
    fun `should create fetch and delete pet`() = runBlocking {
        val petRequest = PetTestDataFactory.newAvailablePet()

        val createdPet = client.addPet(petRequest)
        assertNotNull(createdPet.id, "Created pet should have an id")
        ApiAssertions.assertPetCoreFields(
            expected = petRequest,
            actual = createdPet,
            context = "Created pet",
        )

        val fetchedPet = client.getPetById(createdPet.id!!)
        ApiAssertions.assertPetCoreFields(
            expected = createdPet,
            actual = fetchedPet,
            context = "Fetched pet",
        )

        client.deletePet(createdPet.id!!)

        assertFailsWith<ClientRequestException>(
            message = "Expected fetching deleted pet to fail with ClientRequestException",
        ) {
            client.getPetById(createdPet.id!!)
        }
    }
}

