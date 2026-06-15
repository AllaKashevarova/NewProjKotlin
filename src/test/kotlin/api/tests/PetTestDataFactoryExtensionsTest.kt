package api.tests

import org.junit.jupiter.api.Test
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
}
