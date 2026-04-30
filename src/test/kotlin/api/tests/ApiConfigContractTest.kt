package api.tests

import api.config.ApiConfig
import org.junit.jupiter.api.Test
import kotlin.test.assertTrue

class ApiConfigContractTest {

    @Test
    fun `should use secure base url`() {
        assertTrue(
            ApiConfig.BASE_URL.startsWith("https://"),
            "Expected BASE_URL to use HTTPS",
        )
    }

    @Test
    fun `should keep endpoint paths as absolute api paths`() {
        val endpoints = listOf(
            ApiConfig.PET,
            ApiConfig.PET_BY_ID,
            ApiConfig.PET_UPLOAD_IMAGE,
            ApiConfig.PET_FIND_BY_STATUS,
            ApiConfig.PET_FIND_BY_TAGS,
            ApiConfig.STORE,
            ApiConfig.STORE_INVENTORY,
            ApiConfig.STORE_ORDER,
            ApiConfig.STORE_ORDER_BY_ID,
            ApiConfig.USER,
            ApiConfig.USER_BY_NAME,
            ApiConfig.USER_LOGIN,
            ApiConfig.USER_LOGOUT,
            ApiConfig.USER_CREATE_WITH_ARRAY,
            ApiConfig.USER_CREATE_WITH_LIST,
        )

        assertTrue(
            endpoints.all { it.startsWith("/") && !it.startsWith("//") },
            "Expected each endpoint path to be an absolute API path",
        )
    }
}

