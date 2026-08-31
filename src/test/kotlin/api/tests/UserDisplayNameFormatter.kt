package api.tests

import api.model.User

object UserDisplayNameFormatter {

    fun displayName(user: User): String {
        val first = user.firstName?.trim()?.ifBlank { null }
        val last = user.lastName?.trim()?.ifBlank { null }

        return when {
            first != null && last != null -> "$first $last"
            first != null -> first
            last != null -> last
            else -> user.username.trim()
        }
    }

    fun initials(user: User): String {
        val first = user.firstName?.trim()?.ifBlank { null }
        val last = user.lastName?.trim()?.ifBlank { null }

        val fromNames = listOfNotNull(first, last)
            .map { it.first().uppercaseChar() }
            .joinToString("")

        if (fromNames.isNotEmpty()) return fromNames

        return user.username.trim().firstOrNull()?.uppercaseChar()?.toString() ?: ""
    }
}
