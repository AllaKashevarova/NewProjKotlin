package api.tests

import api.client.PetApiClient
import api.model.PetStatus
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import kotlin.test.assertTrue

class PetFindByStatusFlowTest {

    private val client = PetApiClient()

    @Test
    fun `should return pets that match requested status`() = runBlocking {
        val pets = client.getPetsByStatus(PetStatus.PENDING)
        assertTrue(pets.isNotEmpty(), "Expected at least one pending pet from Petstore API")

        val summary = PetListSummaryBuilder.build(pets)
        assertTrue(summary.byStatus.containsKey("pending"), "Expected pending status in summary")
        assertTrue(
            pets.all { PetStatusParser.normalize(it.status) == PetStatus.PENDING.value },
            "Expected all returned pets to have pending status",
        )
    }
}
