package com.onedev.ojolapi.core.user.repository

import com.onedev.ojolapi.core.user.entity.User

interface UserRepository {

    fun insertUser(user: User): Result<Boolean>
    fun getUserById(id: String): Result<User>
    fun getUserByUsername(username: String): Result<User>
    fun getUserByRole(role: String): Result<List<User>>
    fun updateUser(id: String, name: String, username: String, password: String, role: String, location: String): Result<User>

}