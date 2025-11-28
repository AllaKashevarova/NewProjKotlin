package api.client

import api.config.ApiConfig
import api.model.Order
import io.ktor.client.call.body
import io.ktor.client.request.*
import io.ktor.http.*

class StoreApiClient : BaseApiClient() {

    suspend fun getInventory(): Map<String, Int> =
        client.get("${ApiConfig.BASE_URL}${ApiConfig.STORE_INVENTORY}") {
            accept(ContentType.Application.Json)
        }.body()

    suspend fun placeOrder(order: Order): Order =
        client.post("${ApiConfig.BASE_URL}${ApiConfig.STORE_ORDER}") {
            contentType(ContentType.Application.Json)
            setBody(order)
        }.body()

    suspend fun getOrderById(orderId: Long): Order =
        client.get("${ApiConfig.BASE_URL}${ApiConfig.STORE_ORDER}/$orderId") {
            accept(ContentType.Application.Json)
        }.body()

    suspend fun deleteOrder(orderId: Long) {
        client.delete("${ApiConfig.BASE_URL}${ApiConfig.STORE_ORDER}/$orderId") {
            accept(ContentType.Application.Json)
        }
    }
}
