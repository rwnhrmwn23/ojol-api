package com.onedev.ojolapi.core.user.entity

data class RequestRegister(
    val name: String,
    val username: String,
    val password: String
) {
    fun mapToNewUser(role: String): User {
        return User.createNewUser(name, username, password, role)
    }
}