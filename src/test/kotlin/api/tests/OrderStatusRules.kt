package api.tests

object OrderStatusRules {

    const val PLACED = "placed"
    const val APPROVED = "approved"
    const val DELIVERED = "delivered"

    private val knownStatuses = setOf(PLACED, APPROVED, DELIVERED)
    private val terminalStatuses = setOf(DELIVERED)

    fun isValid(status: String?): Boolean {
        if (status.isNullOrBlank()) return false
        return status.trim().lowercase() in knownStatuses
    }

    fun isTerminal(status: String?): Boolean {
        if (status.isNullOrBlank()) return false
        return status.trim().lowercase() in terminalStatuses
    }

    fun canTransition(from: String?, to: String?): Boolean {
        val fromStatus = from?.trim()?.lowercase() ?: return false
        val toStatus = to?.trim()?.lowercase() ?: return false
        if (!isValid(fromStatus) || !isValid(toStatus)) return false
        if (fromStatus == toStatus) return true

        return when (fromStatus) {
            PLACED -> toStatus in setOf(APPROVED, DELIVERED)
            APPROVED -> toStatus == DELIVERED
            DELIVERED -> false
            else -> false
        }
    }
}
