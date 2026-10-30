package api.tests

import api.model.User

object UserTestDataFactory {

    fun newUpdatableUser(username: String = TestDataFactory.uniqueUsername("userflow")): User =
        User(
            id = null,
            username = username,
            firstName = "Initial",
            lastName = "Profile",
            email = "$username@example.com",
            password = "pass1234",
            phone = "+48111000000",
            userStatus = 1,
        )

    fun newMinimalUser(username: String = TestDataFactory.uniqueUsername("minimal")): User =
        User(username = username)

    fun newUserBatch(count: Int, prefix: String = "batch"): List<User> {
        require(count >= 0) { "count must not be negative" }
        return List(count) { TestDataFactory.newUser(TestDataFactory.uniqueUsername(prefix)) }
    }

    fun updatedUserProfile(original: User): User {
        val username = original.username
        return User(
            id = original.id,
            username = username,
            firstName = "Updated",
            lastName = "Profile",
            email = "$username+updated@example.com",
            password = "newPass1234",
            phone = "+48111999999",
            userStatus = 2,
        )
    }
}

