package api.tests

import api.model.User
import java.util.UUID

object TestDataFactory {
    fun uniqueUsername(prefix: String = "qa"): String =
        "${prefix}_${UUID.randomUUID().toString().replace("-", "").take(12)}"

    fun newUser(username: String = uniqueUsername()): User =
        User(
            id = null,
            username = username,
            firstName = "Auto",
            lastName = "Test",
            email = "$username@example.com",
            password = "pass1234",
            phone = "+48111111111",
            userStatus = 1
        )
}
