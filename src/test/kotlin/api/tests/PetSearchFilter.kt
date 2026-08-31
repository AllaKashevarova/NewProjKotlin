package api.tests

import api.model.Pet

data class PetSearchCriteria(
    val nameContains: String? = null,
    val status: String? = null,
    val tag: String? = null,
)

object PetSearchFilter {

    fun matches(pet: Pet, criteria: PetSearchCriteria): Boolean {
        val nameQuery = criteria.nameContains?.trim()?.ifBlank { null }
        val statusQuery = criteria.status?.trim()?.ifBlank { null }
        val tagQuery = criteria.tag?.trim()?.ifBlank { null }

        if (nameQuery != null && pet.name?.contains(nameQuery, ignoreCase = true) != true) return false
        if (statusQuery != null && !statusQuery.equals(pet.status, ignoreCase = true)) return false
        if (tagQuery != null && !PetTagNormalizer.hasTag(pet, tagQuery)) return false
        return true
    }

    fun filter(pets: List<Pet>, criteria: PetSearchCriteria): List<Pet> =
        pets.filter { matches(it, criteria) }
}
