package api.tests

import api.model.PetStatus

object PetStatusParser {

    fun parse(status: String?): PetStatus? {
        if (status.isNullOrBlank()) return null
        val normalized = status.trim().lowercase()
        return PetStatus.entries.firstOrNull { it.value == normalized }
    }

    fun isKnownStatus(status: String?): Boolean = parse(status) != null

    fun normalize(status: String?): String? = parse(status)?.value
}
