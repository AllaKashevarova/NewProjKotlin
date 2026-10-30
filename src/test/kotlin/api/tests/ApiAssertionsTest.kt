package api.tests

import api.model.Pet
import api.model.Tag
import org.junit.jupiter.api.Test
import kotlin.test.assertFailsWith

class ApiAssertionsTest {

    private fun pet(id: Long, vararg tagNames: String?): Pet =
        Pet(id = id, name = "pet-$id", tags = tagNames.map { Tag(name = it) })

    @Test
    fun `pet tags assertion should ignore case, whitespace and duplicates`() {
        val pet = pet(1, "Friendly", " small ", "FRIENDLY", null)

        ApiAssertions.assertPetTags(pet, setOf("friendly", "SMALL"), "tags")
    }

    @Test
    fun `pet tags assertion should fail when tags differ`() {
        val pet = pet(1, "friendly")

        assertFailsWith<AssertionError> {
            ApiAssertions.assertPetTags(pet, setOf("friendly", "small"), "tags")
        }
    }

    @Test
    fun `pet tags assertion should accept empty expectation for untagged pet`() {
        ApiAssertions.assertPetTags(pet(1), emptySet(), "untagged")
    }

    @Test
    fun `same pet ids assertion should ignore ordering`() {
        ApiAssertions.assertSamePetIds(
            expected = listOf(pet(1), pet(2), pet(3)),
            actual = listOf(pet(3), pet(1), pet(2)),
            context = "list",
        )
    }

    @Test
    fun `same pet ids assertion should fail on missing pet`() {
        assertFailsWith<AssertionError> {
            ApiAssertions.assertSamePetIds(
                expected = listOf(pet(1), pet(2)),
                actual = listOf(pet(1)),
                context = "list",
            )
        }
    }

    @Test
    fun `pet core fields assertion should fail on status mismatch`() {
        val expected = pet(1).copy(status = "available")
        val actual = pet(1).copy(status = "sold")

        assertFailsWith<AssertionError> {
            ApiAssertions.assertPetCoreFields(expected, actual, "status check")
        }
    }
}
