package api.tests

import api.model.User

enum class UserProfileField {
    FIRST_NAME,
    LAST_NAME,
    EMAIL,
    PHONE,
    PASSWORD,
}

data class UserProfileCompleteness(
    val missingFields: Set<UserProfileField>,
    val invalidFields: Set<UserProfileField>,
) {
    val isComplete: Boolean
        get() = missingFields.isEmpty() && invalidFields.isEmpty()

    val completionPercent: Int
        get() {
            val total = UserProfileField.entries.size
            val good = total - (missingFields + invalidFields).size
            return good * 100 / total
        }
}

object UserProfileCompletenessChecker {

    const val MIN_PASSWORD_LENGTH = 8

    fun check(user: User): UserProfileCompleteness {
        val missing = mutableSetOf<UserProfileField>()
        val invalid = mutableSetOf<UserProfileField>()

        classify(UserProfileField.FIRST_NAME, user.firstName, missing, invalid) { it.isNotBlank() }
        classify(UserProfileField.LAST_NAME, user.lastName, missing, invalid) { it.isNotBlank() }
        classify(UserProfileField.EMAIL, user.email, missing, invalid) { UserEmailValidator.isValid(it) }
        classify(UserProfileField.PHONE, user.phone, missing, invalid) { UserPhoneValidator.isValid(it) }
        classify(UserProfileField.PASSWORD, user.password, missing, invalid) { it.length >= MIN_PASSWORD_LENGTH }

        return UserProfileCompleteness(missingFields = missing, invalidFields = invalid)
    }

    fun incompleteUsers(users: List<User>): List<User> =
        users.filterNot { check(it).isComplete }

    fun averageCompletionPercent(users: List<User>): Int {
        if (users.isEmpty()) return 0
        return users.sumOf { check(it).completionPercent } / users.size
    }

    private fun classify(
        field: UserProfileField,
        value: String?,
        missing: MutableSet<UserProfileField>,
        invalid: MutableSet<UserProfileField>,
        isValid: (String) -> Boolean,
    ) {
        when {
            value.isNullOrBlank() -> missing += field
            !isValid(value) -> invalid += field
        }
    }
}
