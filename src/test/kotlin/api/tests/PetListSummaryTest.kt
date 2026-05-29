package api.tests

import api.model.Pet
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class PetListSummaryTest {

    @Test
    fun `should summarize pets by normalized status`() {
        val pets = listOf(
            Pet(id = 1, name = "a", status = "available"),
            Pet(id = 2, name = "b", status = "AVAILABLE"),
            Pet(id = 3, name = "c", status = "sold"),
            Pet(id = 4, name = "d", status = "retired"),
            Pet(id = 5, name = "e", status = null),
        )

        val summary = PetListSummaryBuilder.build(pets)

        assertEquals(5, summary.total)
        assertEquals(2, summary.byStatus["available"])
        assertEquals(1, summary.byStatus["sold"])
        assertEquals(2, summary.unknownStatusCount)
    }
}
