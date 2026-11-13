package api.tests

data class InventoryChange(
    val status: String,
    val before: Int,
    val after: Int,
) {
    val delta: Int
        get() = after - before
}

data class InventoryDiff(val changes: List<InventoryChange>) {

    val isUnchanged: Boolean
        get() = changes.isEmpty()

    val increased: List<InventoryChange>
        get() = changes.filter { it.delta > 0 }

    val decreased: List<InventoryChange>
        get() = changes.filter { it.delta < 0 }

    val netChange: Int
        get() = changes.sumOf { it.delta }

    fun changeFor(status: String): InventoryChange? =
        changes.firstOrNull { it.status == status }
}

object InventorySnapshotDiff {

    fun diff(before: Map<String, Int>, after: Map<String, Int>): InventoryDiff {
        val changes = (before.keys + after.keys)
            .sorted()
            .map { status ->
                InventoryChange(
                    status = status,
                    before = before[status] ?: 0,
                    after = after[status] ?: 0,
                )
            }
            .filter { it.delta != 0 }

        return InventoryDiff(changes)
    }

    fun describe(diff: InventoryDiff): String {
        if (diff.isUnchanged) return "no inventory changes"

        return diff.changes.joinToString(separator = ", ") { change ->
            val sign = if (change.delta > 0) "+" else ""
            "${change.status} ${change.before}->${change.after} ($sign${change.delta})"
        }
    }
}
