package com.onedev.ojolapi.core.user.entity

import java.util.*

data class User(
    var id: String = "",
    var name: String = "",
    var username: String = "",
    var password: String? = "",
    var role: String = "",
    var location: String? = ""
) {
    companion object {
        fun createNewUser(name: String, username: String, password: String, role: String, location: String?): User {
            return User(
                id = UUID.randomUUID().toString(),
                name = name,
                username = username,
                password = password,
                role = role,
                location = location
            )
        }
    }
}