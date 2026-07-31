package api.tests

object UserEmailValidator {

    const val MAX_LENGTH = 254

    // Simple, pragmatic check: single "@", non-empty local part, and a
    // dotted domain with a 2+ letter TLD. Deliberately not full RFC 5322.
    private val emailPattern = Regex("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")

    fun isValid(email: String): Boolean {
        val trimmed = email.trim()
        if (trimmed.isEmpty() || trimmed.length > MAX_LENGTH) return false
        if (trimmed.count { it == '@' } != 1) return false
        return emailPattern.matches(trimmed)
    }

    fun normalize(email: String): String = email.trim().lowercase()
}
