package com.onedev.ojolapi.core.entity

import java.util.UUID

object Register {
    data class Request(
        val name: String,
        val username: String,
        val password: String
    ) {
        fun mapToNewUser(role: String): User {
            return User.createNewUser(name, username, password, role)
        }
    }

    data class User(
        var id: String = "",
        var name: String = "",
        var username: String = "",
        var password: String? = "",
        var role: String = "",
    ) {
        companion object {
            fun createNewUser(name: String, username: String, password: String, role: String): User {
                return User(
                    id = UUID.randomUUID().toString(),
                    name = name,
                    username = username,
                    password = password,
                    role = role,
                )
            }
        }
    }
}
