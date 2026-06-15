package api.tests

import io.ktor.client.statement.HttpResponse

object HttpStatusAssertions {

    fun assertSuccess(response: HttpResponse, context: String) {
        val status = response.status.value
        require(status in 200..299) {
            "$context: expected 2xx status, got $status"
        }
    }

    fun assertStatus(response: HttpResponse, expected: Int, context: String) {
        val actual = response.status.value
        require(actual == expected) {
            "$context: expected HTTP $expected, got $actual"
        }
    }
}
