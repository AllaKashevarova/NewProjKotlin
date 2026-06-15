package api.tests

import org.junit.jupiter.api.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class PetPhotoUrlValidatorTest {

    @Test
    fun `should accept http and https photo urls`() {
        assertTrue(PetPhotoUrlValidator.isValid("https://example.com/photo.jpg"))
        assertTrue(PetPhotoUrlValidator.isValid("http://cdn.example.com/a.png"))
    }

    @Test
    fun `should reject invalid or empty url lists`() {
        assertFalse(PetPhotoUrlValidator.isValid("not-a-url"))
        assertFalse(PetPhotoUrlValidator.allValid(emptyList()))
        assertTrue(
            PetPhotoUrlValidator.allValid(listOf("https://example.com/1.jpg", "https://example.com/2.jpg")),
        )
    }
}
