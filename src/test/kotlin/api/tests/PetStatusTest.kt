package api.tests

import api.client.PetApiClient
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import kotlin.test.assertTrue

class PetStatusTest {

    private val client = PetApiClient()

    @Test
    fun `should return only available pets`() = runBlocking {
        val pets = client.getAvailablePets()

        assertTrue(pets.isNotEmpty(), "Expected at least one available pet")
        // All returned pets should have status "available" (when status is present)
        assertTrue(
            pets.all { it.status == null || it.status == "available" },
            "Expected all pets to have status 'available' when present",
        )
    }
}

