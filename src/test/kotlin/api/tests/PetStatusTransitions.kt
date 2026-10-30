package api.tests

import api.model.PetStatus

object PetStatusTransitions {

    private val allowedTransitions: Map<PetStatus, Set<PetStatus>> = mapOf(
        PetStatus.AVAILABLE to setOf(PetStatus.PENDING, PetStatus.SOLD),
        PetStatus.PENDING to setOf(PetStatus.AVAILABLE, PetStatus.SOLD),
        PetStatus.SOLD to emptySet(),
    )

    fun nextStatuses(from: PetStatus): Set<PetStatus> = allowedTransitions.getValue(from)

    fun isTerminal(status: PetStatus): Boolean = nextStatuses(status).isEmpty()

    fun canTransition(from: PetStatus, to: PetStatus): Boolean =
        from == to || to in nextStatuses(from)

    fun canTransition(from: String?, to: String?): Boolean {
        val fromStatus = PetStatusParser.parse(from) ?: return false
        val toStatus = PetStatusParser.parse(to) ?: return false
        return canTransition(fromStatus, toStatus)
    }

    fun requireTransition(from: String?, to: PetStatus) {
        check(canTransition(from, to.value)) {
            "Pet status transition '$from' -> '${to.value}' is not allowed"
        }
    }

    fun shortestPath(from: PetStatus, to: PetStatus): List<PetStatus>? {
        if (from == to) return listOf(from)

        val previous = mutableMapOf<PetStatus, PetStatus>()
        val visited = mutableSetOf(from)
        val queue = ArrayDeque<PetStatus>().apply { addLast(from) }

        while (queue.isNotEmpty()) {
            val current = queue.removeFirst()
            for (next in nextStatuses(current).sortedBy { it.ordinal }) {
                if (!visited.add(next)) continue
                previous[next] = current
                if (next == to) {
                    return generateSequence(to) { previous[it] }.toList().reversed()
                }
                queue.addLast(next)
            }
        }
        return null
    }
}
