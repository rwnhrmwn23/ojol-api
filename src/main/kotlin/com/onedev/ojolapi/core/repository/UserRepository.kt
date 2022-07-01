package com.onedev.ojolapi.core.repository

import com.onedev.ojolapi.core.entity.Register

interface UserRepository {

    fun insertUser(user: Register.User): Result<Boolean>

    fun getUserById(id: String): Result<Register.User>

    fun getUserByUsername(username: String): Result<Register.User>
    fun getUserByRole(role: String): Result<List<Register.User>>

}