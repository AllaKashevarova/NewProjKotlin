package api.tests

import api.model.PetStatus
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertTrue

class PetStatusParserTest {

    @Test
    fun `should parse known pet statuses case-insensitively`() {
        assertEquals(PetStatus.AVAILABLE, PetStatusParser.parse("Available"))
        assertEquals(PetStatus.SOLD, PetStatusParser.parse(" sold "))
    }

    @Test
    fun `should return null for unknown or blank status`() {
        assertNull(PetStatusParser.parse(null))
        assertNull(PetStatusParser.parse(""))
        assertNull(PetStatusParser.parse("retired"))
    }

    @Test
    fun `should normalize to api status value`() {
        assertEquals("pending", PetStatusParser.normalize("PENDING"))
        assertFalse(PetStatusParser.isKnownStatus("missing"))
        assertTrue(PetStatusParser.isKnownStatus("available"))
    }
}
