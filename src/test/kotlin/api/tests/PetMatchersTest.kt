package api.tests

import api.model.Pet
import api.model.PetStatus
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class PetMatchersTest {

    @Test
    fun `should match pet status and photo rules`() {
        val pet = Pet(
            id = 1,
            name = "auto-pet-abc",
            photoUrls = listOf("https://example.com/1.jpg"),
            status = "available",
        )

        assertTrue(PetMatchers.hasPhotos(pet))
        assertTrue(PetMatchers.matchesStatus(pet, PetStatus.AVAILABLE))
        assertTrue(PetMatchers.nameStartsWith(pet, "auto-pet"))
        assertFalse(PetMatchers.matchesStatus(pet, PetStatus.SOLD))
    }

    @Test
    fun `should filter pets by status and name prefix`() {
        val pets = listOf(
            Pet(id = 1, name = "auto-pet-1", status = "available"),
            Pet(id = 2, name = "auto-pet-2", status = "sold"),
            Pet(id = 3, name = "other-pet", status = "available"),
        )

        assertEquals(2, PetMatchers.filterByStatus(pets, PetStatus.AVAILABLE).size)
        assertEquals(2, PetMatchers.findByNamePrefix(pets, "auto-pet").size)
    }
}
