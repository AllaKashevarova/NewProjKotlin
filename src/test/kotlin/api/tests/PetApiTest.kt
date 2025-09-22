package api.tests

import api.client.PetApiClient
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import kotlin.test.assertTrue

class PetApiTest {

    private val client = PetApiClient()

    @Test
    fun `should return available pets`() = runBlocking {
        val pets = client.getAvailablePets()
        println("Find ${pets.size} available pets")
        assertTrue(pets.isNotEmpty(), "expected at least one available pet")
    }
}
