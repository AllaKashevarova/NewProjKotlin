package api.tests

import api.model.Pet
import api.model.Tag
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class PetSearchFilterTest {

    private fun pet(id: Long, name: String? = null, status: String? = null, vararg tags: String): Pet =
        Pet(id = id, name = name, status = status, tags = tags.mapIndexed { i, n -> Tag(id = i.toLong(), name = n) })

    @Test
    fun `should match name substring case-insensitively`() {
        val criteria = PetSearchCriteria(nameContains = "bud")
        assertTrue(PetSearchFilter.matches(pet(1L, name = "Buddy"), criteria))
        assertFalse(PetSearchFilter.matches(pet(2L, name = "Rex"), criteria))
    }

    @Test
    fun `should not match name criterion when pet name is null`() {
        val criteria = PetSearchCriteria(nameContains = "bud")
        assertFalse(PetSearchFilter.matches(pet(1L, name = null), criteria))
    }

    @Test
    fun `should match status case-insensitively with trimming`() {
        val criteria = PetSearchCriteria(status = " AVAILABLE ")
        assertTrue(PetSearchFilter.matches(pet(1L, name = "Buddy", status = "available"), criteria))
        assertFalse(PetSearchFilter.matches(pet(2L, name = "Rex", status = "sold"), criteria))
    }

    @Test
    fun `should match tag via normalized tag names`() {
        val criteria = PetSearchCriteria(tag = "Friendly")
        assertTrue(PetSearchFilter.matches(pet(1L, name = "Buddy", status = null, "  friendly "), criteria))
        assertFalse(PetSearchFilter.matches(pet(2L, name = "Rex", status = null, "trained"), criteria))
    }

    @Test
    fun `should require all provided criteria to match`() {
        val criteria = PetSearchCriteria(nameContains = "bud", status = "available", tag = "friendly")
        assertTrue(PetSearchFilter.matches(pet(1L, name = "Buddy", status = "available", "friendly"), criteria))
        assertFalse(PetSearchFilter.matches(pet(2L, name = "Buddy", status = "sold", "friendly"), criteria))
        assertFalse(PetSearchFilter.matches(pet(3L, name = "Buddy", status = "available", "trained"), criteria))
    }

    @Test
    fun `should treat blank and null criteria as no filter`() {
        val everything = PetSearchCriteria(nameContains = "   ", status = null, tag = "")
        assertTrue(PetSearchFilter.matches(pet(1L), everything))
        assertTrue(PetSearchFilter.matches(pet(2L, name = "Rex", status = "sold"), everything))
    }

    @Test
    fun `filter should keep only matching pets in original order`() {
        val pets = listOf(
            pet(1L, name = "Buddy", status = "available"),
            pet(2L, name = "Rex", status = "sold"),
            pet(3L, name = "Buddy Jr", status = "available"),
        )
        val result = PetSearchFilter.filter(pets, PetSearchCriteria(nameContains = "buddy", status = "available"))
        assertEquals(listOf(1L, 3L), result.map { it.id })
    }

    @Test
    fun `filter should return empty list when nothing matches`() {
        val pets = listOf(pet(1L, name = "Buddy"), pet(2L, name = "Rex"))
        assertEquals(emptyList(), PetSearchFilter.filter(pets, PetSearchCriteria(nameContains = "milo")))
    }
}
