package api.tests

import api.model.Pet
import api.model.PetStatus
import api.model.Tag
import java.util.UUID

object PetTestDataFactory {

    fun newAvailablePet(
        namePrefix: String = "auto-pet",
        status: PetStatus = PetStatus.AVAILABLE,
    ): Pet {
        val suffix = UUID.randomUUID().toString().replace("-", "").take(8)
        val uniqueName = PetNameValidator.sanitize("$namePrefix-$suffix")
        val id = System.currentTimeMillis()

        return Pet(
            id = id,
            name = uniqueName,
            photoUrls = listOf("https://example.com/photo/$uniqueName"),
            tags = emptyList(),
            status = status.value,
        )
    }

    fun newPendingPet(namePrefix: String = "auto-pending"): Pet =
        newAvailablePet(namePrefix = namePrefix, status = PetStatus.PENDING)

    fun withTags(pet: Pet, tagNames: List<String>): Pet {
        val tags = tagNames.mapIndexed { index, name ->
            Tag(id = index.toLong() + 1, name = name)
        }
        return pet.copy(tags = tags)
    }

    fun withStatus(pet: Pet, status: PetStatus): Pet =
        pet.copy(status = status.value)
}

