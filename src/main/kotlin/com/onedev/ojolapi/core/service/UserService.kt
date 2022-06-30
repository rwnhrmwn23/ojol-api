package com.onedev.ojolapi.core.service

import com.onedev.ojolapi.core.entity.Login
import com.onedev.ojolapi.core.entity.Register

interface UserService {
    fun login(userLogin: Login.Request): Result<Login.Response>
    fun register(user: Register.User): Result<Boolean>
    fun getUserById(id: String): Result<Register.User>
    fun getUserByUsername(username: String): Result<Register.User>
}