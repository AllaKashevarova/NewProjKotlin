package api.tests

object UserCredentialsValidator {

    const val MIN_USERNAME_LENGTH = 3
    const val MAX_USERNAME_LENGTH = 32
    const val MIN_PASSWORD_LENGTH = 6
    const val MAX_PASSWORD_LENGTH = 64

    private val usernamePattern = Regex("^[a-zA-Z0-9][a-zA-Z0-9_-]*$")

    fun isValidUsername(username: String): Boolean {
        val trimmed = username.trim()
        if (trimmed.length !in MIN_USERNAME_LENGTH..MAX_USERNAME_LENGTH) return false
        return usernamePattern.matches(trimmed)
    }

    fun isValidPassword(password: String): Boolean {
        val trimmed = password.trim()
        return trimmed.length in MIN_PASSWORD_LENGTH..MAX_PASSWORD_LENGTH
    }

    fun isValidCredentials(username: String, password: String): Boolean =
        isValidUsername(username) && isValidPassword(password)
}
