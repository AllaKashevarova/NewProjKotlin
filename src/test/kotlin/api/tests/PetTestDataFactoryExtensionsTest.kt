package api.tests

import api.model.PetStatus
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class PetTestDataFactoryExtensionsTest {

    @Test
    fun `should build pending pet with valid photo urls`() {
        val pet = PetTestDataFactory.newPendingPet()
        assertEquals("pending", pet.status)
        assertTrue(PetPhotoUrlValidator.allValid(pet.photoUrls))
    }

    @Test
    fun `should attach tags to pet payload`() {
        val pet = PetTestDataFactory.withTags(
            PetTestDataFactory.newAvailablePet(),
            listOf("qa", "automation"),
        )
        assertEquals(2, pet.tags.size)
        assertEquals("qa", pet.tags[0].name)
    }

    @Test
    fun `should build sold pet`() {
        assertEquals("sold", PetTestDataFactory.newSoldPet().status)
    }

    @Test
    fun `should build batch of pets with unique ids and names`() {
        val pets = PetTestDataFactory.newPetBatch(count = 5, status = PetStatus.PENDING)

        assertEquals(5, pets.size)
        assertEquals(5, pets.map { it.id }.toSet().size)
        assertEquals(5, pets.map { it.name }.toSet().size)
        assertTrue(pets.all { it.status == "pending" })
    }

    @Test
    fun `should build empty batch for zero count`() {
        assertTrue(PetTestDataFactory.newPetBatch(count = 0).isEmpty())
    }

    @Test
    fun `should reject negative batch size`() {
        assertThrows<IllegalArgumentException> { PetTestDataFactory.newPetBatch(count = -1) }
    }

    @Test
    fun `should transition pet along an allowed path`() {
        val pet = PetTestDataFactory.newAvailablePet()
        val sold = PetTestDataFactory.transitionedTo(pet, PetStatus.SOLD)

        assertEquals("sold", sold.status)
        assertEquals(pet.id, sold.id)
    }

    @Test
    fun `should refuse to transition a sold pet back to available`() {
        val sold = PetTestDataFactory.newSoldPet()

        assertThrows<IllegalStateException> {
            PetTestDataFactory.transitionedTo(sold, PetStatus.AVAILABLE)
        }
    }
}
