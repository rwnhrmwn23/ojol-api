package com.onedev.ojolapi.core.entity

import java.util.UUID

object Register {
    data class Request(
        val username: String,
        val password: String
    ) {
        fun mapToNewUser(role: String): User {
            return User.createNewUser(username, password, role)
        }
    }

    data class User(
        var id: String = "",
        var username: String = "",
        var password: String? = "",
        var role: String = "",
    ) {
        companion object {
            fun createNewUser(username: String, password: String, role: String): User {
                return User(
                    id = UUID.randomUUID().toString(),
                    username = username,
                    password = password,
                    role = role,
                )
            }
        }
    }
}
