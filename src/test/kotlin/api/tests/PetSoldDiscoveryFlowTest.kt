package api.tests

import api.client.PetApiClient
import api.model.PetStatus
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class PetSoldDiscoveryFlowTest {

    private val client = PetApiClient()

    @Test
    fun `should find created sold pet in sold status search`() = runBlocking {
        val createdPet = client.addPet(
            PetTestDataFactory.newAvailablePet(namePrefix = "auto-sold", status = PetStatus.SOLD),
        )
        assertNotNull(createdPet.id, "Created pet should have an id")

        try {
            val soldPets = client.getPetsByStatus(PetStatus.SOLD)
            assertTrue(soldPets.isNotEmpty(), "Expected at least one sold pet from API")

            val matching = PetMatchers.findByNamePrefix(soldPets, "auto-sold")
            assertTrue(
                matching.any { it.id == createdPet.id },
                "Expected sold pets list to include the created sold pet",
            )
        } finally {
            runCatching { client.deletePet(createdPet.id) }
        }
    }
}
