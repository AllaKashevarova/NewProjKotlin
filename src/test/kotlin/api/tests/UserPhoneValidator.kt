package api.tests

object UserPhoneValidator {

    private const val MIN_DIGITS = 7
    private const val MAX_DIGITS = 15

    fun normalize(phone: String?): String? {
        if (phone.isNullOrBlank()) return null

        val trimmed = phone.trim()
        val plus = if (trimmed.startsWith("+")) "+" else ""
        val digits = trimmed.filter { it.isDigit() }

        if (digits.isEmpty()) return null

        return plus + digits
    }

    fun isValid(phone: String?): Boolean {
        val trimmed = phone?.trim() ?: return false
        if (trimmed.isEmpty()) return false

        val allowed = trimmed.drop(if (trimmed.startsWith("+")) 1 else 0)
            .all { it.isDigit() || it in " -()." }
        if (!allowed) return false

        val digits = trimmed.count { it.isDigit() }
        return digits in MIN_DIGITS..MAX_DIGITS
    }
}
