package api.tests

import api.model.Pet
import api.model.PetStatus

object PetMatchers {

    fun hasPhotos(pet: Pet): Boolean = pet.photoUrls.isNotEmpty()

    fun matchesStatus(pet: Pet, status: PetStatus): Boolean =
        PetStatusParser.normalize(pet.status) == status.value

    fun nameStartsWith(pet: Pet, prefix: String): Boolean =
        pet.name?.startsWith(prefix) == true

    fun filterByStatus(pets: List<Pet>, status: PetStatus): List<Pet> =
        pets.filter { matchesStatus(it, status) }

    fun findByNamePrefix(pets: List<Pet>, prefix: String): List<Pet> =
        pets.filter { nameStartsWith(it, prefix) }
}
