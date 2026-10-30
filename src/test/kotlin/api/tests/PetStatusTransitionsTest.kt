package api.tests

import api.model.PetStatus
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertTrue

class PetStatusTransitionsTest {

    @Test
    fun `available pet can move to pending or sold`() {
        assertEquals(
            setOf(PetStatus.PENDING, PetStatus.SOLD),
            PetStatusTransitions.nextStatuses(PetStatus.AVAILABLE),
        )
    }

    @Test
    fun `pending pet can return to available or be sold`() {
        assertTrue(PetStatusTransitions.canTransition(PetStatus.PENDING, PetStatus.AVAILABLE))
        assertTrue(PetStatusTransitions.canTransition(PetStatus.PENDING, PetStatus.SOLD))
    }

    @Test
    fun `sold is terminal and cannot move anywhere else`() {
        assertTrue(PetStatusTransitions.isTerminal(PetStatus.SOLD))
        assertFalse(PetStatusTransitions.canTransition(PetStatus.SOLD, PetStatus.AVAILABLE))
        assertFalse(PetStatusTransitions.canTransition(PetStatus.SOLD, PetStatus.PENDING))
    }

    @Test
    fun `non-terminal statuses are not terminal`() {
        assertFalse(PetStatusTransitions.isTerminal(PetStatus.AVAILABLE))
        assertFalse(PetStatusTransitions.isTerminal(PetStatus.PENDING))
    }

    @Test
    fun `staying in the same status is always allowed`() {
        PetStatus.entries.forEach { status ->
            assertTrue(PetStatusTransitions.canTransition(status, status), "Expected $status -> $status to be allowed")
        }
    }

    @Test
    fun `string transitions are parsed case-insensitively`() {
        assertTrue(PetStatusTransitions.canTransition(" Available ", "PENDING"))
        assertFalse(PetStatusTransitions.canTransition("sold", "available"))
    }

    @Test
    fun `string transitions reject unknown or missing statuses`() {
        assertFalse(PetStatusTransitions.canTransition("available", "adopted"))
        assertFalse(PetStatusTransitions.canTransition(null, "sold"))
        assertFalse(PetStatusTransitions.canTransition("available", null))
        assertFalse(PetStatusTransitions.canTransition("", ""))
    }

    @Test
    fun `requireTransition should pass for allowed transition`() {
        PetStatusTransitions.requireTransition("available", PetStatus.SOLD)
    }

    @Test
    fun `requireTransition should fail for forbidden transition`() {
        val error = assertThrows<IllegalStateException> {
            PetStatusTransitions.requireTransition("sold", PetStatus.AVAILABLE)
        }
        assertTrue(error.message.orEmpty().contains("sold"))
    }

    @Test
    fun `shortest path should be a single step when directly reachable`() {
        assertEquals(
            listOf(PetStatus.AVAILABLE, PetStatus.SOLD),
            PetStatusTransitions.shortestPath(PetStatus.AVAILABLE, PetStatus.SOLD),
        )
    }

    @Test
    fun `shortest path to the same status contains only that status`() {
        assertEquals(
            listOf(PetStatus.PENDING),
            PetStatusTransitions.shortestPath(PetStatus.PENDING, PetStatus.PENDING),
        )
    }

    @Test
    fun `shortest path should be null when target is unreachable`() {
        assertNull(PetStatusTransitions.shortestPath(PetStatus.SOLD, PetStatus.AVAILABLE))
    }
}
