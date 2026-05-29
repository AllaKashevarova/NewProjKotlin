package api.tests

import api.config.ApiConfig
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class ApiUrlBuilderTest {

    @Test
    fun `should build endpoint url from base and path`() {
        val url = ApiUrlBuilder.endpoint("https://petstore.swagger.io/v2/", "/pet")
        assertEquals("https://petstore.swagger.io/v2/pet", url)
    }

    @Test
    fun `should substitute path parameter placeholder`() {
        val path = ApiUrlBuilder.withPathParam(ApiConfig.PET_BY_ID, "petId", "42")
        assertEquals("/pet/42", path)
    }

    @Test
    fun `should build full pet url from config constants`() {
        val url = ApiUrlBuilder.endpoint(ApiConfig.BASE_URL, ApiConfig.STORE_INVENTORY)
        assertEquals("https://petstore.swagger.io/v2/store/inventory", url)
    }
}
