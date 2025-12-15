package api.config

/**
 * Backward-compat alias for the typo used in PetApiClient.kt
 * so you don't need to edit existing files (avoids merge conflicts).
 */
val ApiConfig.PETS_BY_STATUS: String
    get() = PET_FIND_BY_STATUS
