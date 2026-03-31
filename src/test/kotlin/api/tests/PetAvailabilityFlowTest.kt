package api.tests

import api.client.PetApiClient
import api.model.PetStatus
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class PetAvailabilityFlowTest {

    private val client = PetApiClient()

    @Test
    fun `should include created available pet in available list`() = runBlocking {
        val petRequest = PetTestDataFactory.newAvailablePet()
        val createdPet = client.addPet(petRequest)
        assertNotNull(createdPet.id, "Created pet should have an id")

        try {
            val availablePets = client.getPetsByStatus(PetStatus.AVAILABLE)
            assertTrue(
                availablePets.any { it.id == createdPet.id },
                "Expected available pets list to include the created pet",
            )
        } finally {
            runCatching { client.deletePet(createdPet.id!!) }
        }
    }
}

