package com.onedev.ojolapi.core.user.service

import com.onedev.ojolapi.core.user.entity.RequestLogin
import com.onedev.ojolapi.core.user.entity.ResponseLogin
import com.onedev.ojolapi.core.user.entity.User

interface UserService {
    fun login(role: String, userLogin: RequestLogin): Result<ResponseLogin>
    fun register(user: User): Result<Boolean>
    fun getUserById(id: String): Result<User>
    fun getUserByUsername(username: String): Result<User>
    fun getUserByRole(id: String, role: String): Result<List<User>>
    fun updateUser(id: String, user: User): Result<User>
}