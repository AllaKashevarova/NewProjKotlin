package api.tests

import api.model.Pet
import api.model.Tag
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class PetProfileCompletenessCheckerTest {

    @Test
    fun `should report a fully complete profile as listable with a perfect score`() {
        val pet = Pet(
            id = 1,
            name = "Rex",
            photoUrls = listOf("https://cdn.example.com/rex.png"),
            tags = listOf(Tag(id = 1, name = "friendly")),
            status = "available",
        )

        val report = PetProfileCompletenessChecker.evaluate(pet)

        assertEquals(100, report.score)
        assertTrue(report.missingFields.isEmpty())
        assertTrue(report.isListable)
    }

    @Test
    fun `should flag every missing field for an empty profile`() {
        val pet = Pet(
            id = 2,
            name = "   ",
            photoUrls = listOf("  "),
            tags = listOf(Tag(id = 1, name = null)),
            status = "unknown-status",
        )

        val report = PetProfileCompletenessChecker.evaluate(pet)

        assertEquals(0, report.score)
        assertEquals(
            listOf("name", "photoUrls", "tags", "status"),
            report.missingFields,
        )
        assertFalse(report.isListable)
    }

    @Test
    fun `should be listable but not complete when only tags are missing`() {
        val pet = Pet(
            id = 3,
            name = "Bella",
            photoUrls = listOf("https://cdn.example.com/bella.png"),
            tags = emptyList(),
            status = "pending",
        )

        val report = PetProfileCompletenessChecker.evaluate(pet)

        assertEquals(75, report.score)
        assertEquals(listOf("tags"), report.missingFields)
        assertTrue(report.isListable)
    }

    @Test
    fun `should not be listable when a required field is missing`() {
        val pet = Pet(
            id = 4,
            name = "Milo",
            photoUrls = emptyList(),
            tags = listOf(Tag(id = 1, name = "calm")),
            status = "sold",
        )

        assertFalse(PetProfileCompletenessChecker.isListable(pet))
    }
}
