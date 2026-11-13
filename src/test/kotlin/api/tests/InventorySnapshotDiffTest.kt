package api.tests

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertTrue

class InventorySnapshotDiffTest {

    @Test
    fun `identical snapshots should produce no changes`() {
        val snapshot = mapOf("available" to 5, "sold" to 2)

        val diff = InventorySnapshotDiff.diff(snapshot, snapshot)

        assertTrue(diff.isUnchanged)
        assertEquals(0, diff.netChange)
    }

    @Test
    fun `should report changed counts with deltas`() {
        val before = mapOf("available" to 10, "pending" to 2)
        val after = mapOf("available" to 7, "pending" to 5)

        val diff = InventorySnapshotDiff.diff(before, after)

        assertEquals(-3, diff.changeFor("available")?.delta)
        assertEquals(3, diff.changeFor("pending")?.delta)
        assertEquals(0, diff.netChange)
    }

    @Test
    fun `should treat statuses missing from a snapshot as zero`() {
        val diff = InventorySnapshotDiff.diff(
            before = mapOf("available" to 4),
            after = mapOf("sold" to 3),
        )

        assertEquals(InventoryChange("available", before = 4, after = 0), diff.changeFor("available"))
        assertEquals(InventoryChange("sold", before = 0, after = 3), diff.changeFor("sold"))
    }

    @Test
    fun `missing status and explicit zero should not count as a change`() {
        val diff = InventorySnapshotDiff.diff(mapOf("sold" to 0), emptyMap())

        assertTrue(diff.isUnchanged)
        assertNull(diff.changeFor("sold"))
    }

    @Test
    fun `should split changes into increases and decreases`() {
        val diff = InventorySnapshotDiff.diff(
            before = mapOf("available" to 5, "pending" to 1, "sold" to 4),
            after = mapOf("available" to 9, "pending" to 0, "sold" to 4),
        )

        assertEquals(listOf("available"), diff.increased.map { it.status })
        assertEquals(listOf("pending"), diff.decreased.map { it.status })
        assertEquals(3, diff.netChange)
    }

    @Test
    fun `changes should be sorted by status name`() {
        val diff = InventorySnapshotDiff.diff(
            before = emptyMap(),
            after = mapOf("sold" to 1, "available" to 1, "pending" to 1),
        )

        assertEquals(listOf("available", "pending", "sold"), diff.changes.map { it.status })
    }

    @Test
    fun `should describe changes in readable form`() {
        val diff = InventorySnapshotDiff.diff(
            before = mapOf("available" to 10, "sold" to 1),
            after = mapOf("available" to 8, "sold" to 4),
        )

        assertEquals(
            "available 10->8 (-2), sold 1->4 (+3)",
            InventorySnapshotDiff.describe(diff),
        )
    }

    @Test
    fun `should describe empty diff`() {
        val diff = InventorySnapshotDiff.diff(emptyMap(), emptyMap())

        assertEquals("no inventory changes", InventorySnapshotDiff.describe(diff))
        assertFalse(InventorySnapshotDiff.describe(diff).isBlank())
    }
}
