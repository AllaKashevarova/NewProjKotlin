package api.tests

import api.client.PetApiClient
import api.model.PetStatus
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class PetStatusUpdateFlowTest {

    private val client = PetApiClient()

    @Test
    fun `should update pet status from available to sold`() = runBlocking {
        val createdPet = client.addPet(PetTestDataFactory.newAvailablePet())
        assertNotNull(createdPet.id, "Created pet should have an id")

        try {
            val soldPet = PetTestDataFactory.withStatus(createdPet, PetStatus.SOLD)
            val updatedPet = client.updatePet(soldPet)

            assertEquals(PetStatus.SOLD.value, updatedPet.status, "Expected updated pet to be sold")

            val fetchedPet = client.getPetById(createdPet.id)
            assertEquals(PetStatus.SOLD.value, fetchedPet.status, "Expected fetched pet to remain sold")
            assertTrue(PetMatchers.matchesStatus(fetchedPet, PetStatus.SOLD))
        } finally {
            runCatching { client.deletePet(createdPet.id) }
        }
    }
}
