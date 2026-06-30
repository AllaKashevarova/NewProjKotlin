package api.tests

import api.model.Pet

/**
 * Result of evaluating how complete a [Pet] profile is.
 *
 * @property score completeness as a percentage in the range 0..100
 * @property missingFields names of the fields that are absent or invalid
 * @property isListable whether the pet has the minimum data required to be
 *   shown in the store (a name, at least one photo and a recognised status)
 */
data class PetProfileReport(
    val score: Int,
    val missingFields: List<String>,
    val isListable: Boolean,
)

/**
 * Scores a [Pet] profile against the fields the store cares about:
 * name, photo URLs, tags and a recognised status. Tags are treated as
 * "nice to have" and so do not affect listability, while the other three
 * fields are required.
 */
object PetProfileCompletenessChecker {

    private const val FIELD_COUNT = 4

    fun evaluate(pet: Pet): PetProfileReport {
        val missing = mutableListOf<String>()

        val hasName = !pet.name.isNullOrBlank()
        if (!hasName) missing.add("name")

        val hasPhotos = pet.photoUrls.any { it.isNotBlank() }
        if (!hasPhotos) missing.add("photoUrls")

        val hasTags = pet.tags.any { !it.name.isNullOrBlank() }
        if (!hasTags) missing.add("tags")

        val hasStatus = PetStatusParser.isKnownStatus(pet.status)
        if (!hasStatus) missing.add("status")

        val presentFields = FIELD_COUNT - missing.size

        return PetProfileReport(
            score = presentFields * 100 / FIELD_COUNT,
            missingFields = missing,
            isListable = hasName && hasPhotos && hasStatus,
        )
    }

    fun isListable(pet: Pet): Boolean = evaluate(pet).isListable
}
