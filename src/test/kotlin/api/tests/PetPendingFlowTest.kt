package api.tests

import api.client.PetApiClient
import api.model.PetStatus
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class PetPendingFlowTest {

    private val client = PetApiClient()

    @Test
    fun `should create pending pet and find it in pending search`() = runBlocking {
        val petRequest = PetTestDataFactory.newPendingPet()
        assertTrue(PetPhotoUrlValidator.allValid(petRequest.photoUrls))

        val createdPet = client.addPet(petRequest)
        assertNotNull(createdPet.id, "Created pet should have an id")

        try {
            val pendingPets = client.getPetsByStatus(PetStatus.PENDING)
            assertTrue(
                pendingPets.any { it.id == createdPet.id },
                "Expected pending pets list to include the created pet",
            )
            assertTrue(PetMatchers.matchesStatus(createdPet, PetStatus.PENDING))
        } finally {
            runCatching { client.deletePet(createdPet.id) }
        }
    }
}
