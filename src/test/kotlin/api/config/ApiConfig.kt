package api.config

object ApiConfig {

    // Base URL for Petstore
    const val BASE_URL = "https://petstore.swagger.io/v2"

    // -----------------------
    // PET
    // -----------------------
    const val PET = "/pet"
    const val PET_BY_ID = "/pet/{petId}"
    const val PET_UPLOAD_IMAGE = "/pet/{petId}/uploadImage"
    const val PET_FIND_BY_STATUS = "/pet/findByStatus"
    const val PET_FIND_BY_TAGS = "/pet/findByTags"


    // -----------------------
    // STORE
    // -----------------------
    const val STORE = "/store"
    const val STORE_INVENTORY = "/store/inventory"
    const val STORE_ORDER = "/store/order"
    const val STORE_ORDER_BY_ID = "/store/order/{orderId}"


    // -----------------------
    // USER
    // -----------------------
    const val USER = "/user"
    const val USER_BY_NAME = "/user/{username}"
    const val USER_LOGIN = "/user/login"
    const val USER_LOGOUT = "/user/logout"
    const val USER_CREATE_WITH_ARRAY = "/user/createWithArray"
    const val USER_CREATE_WITH_LIST = "/user/createWithList"
}
