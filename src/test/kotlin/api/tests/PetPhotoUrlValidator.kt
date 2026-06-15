package api.tests

object PetPhotoUrlValidator {

    private val httpUrlPattern = Regex("^https?://\\S+$")

    fun isValid(url: String): Boolean = httpUrlPattern.matches(url.trim())

    fun allValid(urls: List<String>): Boolean = urls.isNotEmpty() && urls.all(::isValid)
}
