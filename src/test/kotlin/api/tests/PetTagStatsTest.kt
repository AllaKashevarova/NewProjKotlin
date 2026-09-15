package api.tests

import api.model.Pet
import api.model.Tag
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class PetTagStatsTest {

    private fun pet(id: Long, vararg tagNames: String?): Pet =
        Pet(id = id, name = "pet-$id", tags = tagNames.map { Tag(name = it) })

    @Test
    fun `should count pets per normalized tag`() {
        val pets = listOf(
            pet(1, "Friendly", "small"),
            pet(2, "friendly"),
            pet(3, "small"),
        )

        assertEquals(mapOf("friendly" to 2, "small" to 2), PetTagStats.tagCounts(pets))
    }

    @Test
    fun `should count duplicate tags on one pet only once`() {
        val pets = listOf(pet(1, "friendly", "FRIENDLY", " friendly "))

        assertEquals(mapOf("friendly" to 1), PetTagStats.tagCounts(pets))
    }

    @Test
    fun `should ignore null and blank tag names`() {
        val pets = listOf(pet(1, null, "  ", "trained"))

        assertEquals(mapOf("trained" to 1), PetTagStats.tagCounts(pets))
    }

    @Test
    fun `should return empty counts for empty pet list`() {
        assertTrue(PetTagStats.tagCounts(emptyList()).isEmpty())
    }

    @Test
    fun `top tags should be ordered by frequency`() {
        val pets = listOf(
            pet(1, "small", "friendly"),
            pet(2, "small"),
            pet(3, "small", "friendly"),
            pet(4, "trained"),
        )

        assertEquals(listOf("small", "friendly", "trained"), PetTagStats.topTags(pets, 3))
    }

    @Test
    fun `top tags should break frequency ties alphabetically`() {
        val pets = listOf(pet(1, "zebra-print", "aggressive"))

        assertEquals(listOf("aggressive", "zebra-print"), PetTagStats.topTags(pets, 2))
    }

    @Test
    fun `top tags should respect the limit`() {
        val pets = listOf(pet(1, "a", "b", "c"))

        assertEquals(1, PetTagStats.topTags(pets, 1).size)
        assertTrue(PetTagStats.topTags(pets, 0).isEmpty())
    }

    @Test
    fun `top tags should return all tags when limit exceeds tag count`() {
        val pets = listOf(pet(1, "a", "b"))

        assertEquals(listOf("a", "b"), PetTagStats.topTags(pets, 10))
    }

    @Test
    fun `top tags should reject negative limit`() {
        assertThrows<IllegalArgumentException> { PetTagStats.topTags(emptyList(), -1) }
    }

    @Test
    fun `should count pets without usable tags as untagged`() {
        val pets = listOf(
            pet(1, "friendly"),
            pet(2),
            pet(3, null, "  "),
        )

        assertEquals(2, PetTagStats.untaggedCount(pets))
    }

    @Test
    fun `untagged count should be zero for empty list`() {
        assertEquals(0, PetTagStats.untaggedCount(emptyList()))
    }
}
