package api.tests

import api.model.Pet
import api.model.Tag

object PetTagNormalizer {

    fun normalize(tags: List<Tag>): List<String> =
        tags.mapNotNull { it.name?.trim()?.lowercase()?.ifBlank { null } }
            .distinct()

    fun normalize(pet: Pet): List<String> = normalize(pet.tags)

    fun hasTag(pet: Pet, tagName: String): Boolean {
        val target = tagName.trim().lowercase()
        if (target.isEmpty()) return false
        return normalize(pet).contains(target)
    }
}
