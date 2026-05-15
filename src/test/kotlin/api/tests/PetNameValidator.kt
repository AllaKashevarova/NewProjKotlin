package api.tests

object PetNameValidator {

    const val MIN_LENGTH = 1
    const val MAX_LENGTH = 64

    private val allowedNamePattern = Regex("^[a-zA-Z0-9][a-zA-Z0-9 _-]*$")

    fun isValid(name: String): Boolean {
        val trimmed = name.trim()
        if (trimmed.length !in MIN_LENGTH..MAX_LENGTH) return false
        return allowedNamePattern.matches(trimmed)
    }

    fun sanitize(name: String): String {
        val collapsed = name.trim().replace(Regex("\\s+"), " ")
        val safeChars = collapsed.filter { it.isLetterOrDigit() || it == ' ' || it == '-' || it == '_' }
        return safeChars.take(MAX_LENGTH).ifBlank { "pet" }
    }
}
