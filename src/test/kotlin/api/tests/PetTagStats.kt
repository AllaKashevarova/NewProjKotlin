package api.tests

import api.model.Pet

object PetTagStats {

    fun tagCounts(pets: List<Pet>): Map<String, Int> =
        pets.flatMap { PetTagNormalizer.normalize(it) }
            .groupingBy { it }
            .eachCount()

    fun topTags(pets: List<Pet>, limit: Int): List<String> {
        require(limit >= 0) { "limit must not be negative" }

        return tagCounts(pets).entries
            .sortedWith(compareByDescending<Map.Entry<String, Int>> { it.value }.thenBy { it.key })
            .take(limit)
            .map { it.key }
    }

    fun untaggedCount(pets: List<Pet>): Int =
        pets.count { PetTagNormalizer.normalize(it).isEmpty() }
}
