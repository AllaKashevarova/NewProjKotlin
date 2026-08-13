package api.tests

import api.model.Pet
import api.model.Tag
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class PetTagNormalizerTest {

    private fun petWithTags(vararg names: String?): Pet =
        Pet(id = 1L, name = "buddy", tags = names.mapIndexed { i, n -> Tag(id = i.toLong(), name = n) })

    @Test
    fun `should trim lowercase and deduplicate tag names`() {
        val pet = petWithTags("  Friendly ", "friendly", "CUTE")
        assertEquals(listOf("friendly", "cute"), PetTagNormalizer.normalize(pet))
    }

    @Test
    fun `should drop null and blank tag names`() {
        val pet = petWithTags(null, "   ", "trained")
        assertEquals(listOf("trained"), PetTagNormalizer.normalize(pet))
    }

    @Test
    fun `should return empty list when pet has no tags`() {
        assertEquals(emptyList(), PetTagNormalizer.normalize(Pet(id = 2L)))
    }

    @Test
    fun `should preserve first-seen order of tags`() {
        val pet = petWithTags("Zeta", "alpha", "zeta")
        assertEquals(listOf("zeta", "alpha"), PetTagNormalizer.normalize(pet))
    }

    @Test
    fun `hasTag should match case-insensitively and ignore surrounding whitespace`() {
        val pet = petWithTags("Friendly")
        assertTrue(PetTagNormalizer.hasTag(pet, "  FRIENDLY "))
        assertFalse(PetTagNormalizer.hasTag(pet, "aggressive"))
    }

    @Test
    fun `hasTag should reject blank queries`() {
        val pet = petWithTags("friendly")
        assertFalse(PetTagNormalizer.hasTag(pet, "   "))
    }
}
