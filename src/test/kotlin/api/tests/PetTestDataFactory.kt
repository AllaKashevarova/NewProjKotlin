package api.tests

import api.model.Pet
import api.model.PetStatus
import java.util.UUID

object PetTestDataFactory {

    fun newAvailablePet(
        namePrefix: String = "auto-pet",
        status: PetStatus = PetStatus.AVAILABLE,
    ): Pet {
        val uniqueName = "$namePrefix-${UUID.randomUUID().toString().replace("-", "").take(8)}"
        val id = System.currentTimeMillis()

        return Pet(
            id = id,
            name = uniqueName,
            photoUrls = listOf("https://example.com/photo/$uniqueName"),
            tags = emptyList(),
            status = status.value,
        )
    }
}

