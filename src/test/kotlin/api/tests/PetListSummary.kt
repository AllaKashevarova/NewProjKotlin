package api.tests

import api.model.Pet

data class PetListSummary(
    val total: Int,
    val byStatus: Map<String, Int>,
    val unknownStatusCount: Int,
)

object PetListSummaryBuilder {

    fun build(pets: List<Pet>): PetListSummary {
        val grouped = pets.groupingBy { pet ->
            PetStatusParser.normalize(pet.status) ?: "unknown"
        }.eachCount()

        val unknownCount = grouped["unknown"] ?: 0
        val knownCounts = grouped.filterKeys { it != "unknown" }

        return PetListSummary(
            total = pets.size,
            byStatus = knownCounts,
            unknownStatusCount = unknownCount,
        )
    }
}
