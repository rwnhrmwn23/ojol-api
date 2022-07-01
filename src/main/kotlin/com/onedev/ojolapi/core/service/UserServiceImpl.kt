package com.onedev.ojolapi.core.service

import com.onedev.ojolapi.authentication.JwtConfig
import com.onedev.ojolapi.core.entity.Login
import com.onedev.ojolapi.core.entity.Register
import com.onedev.ojolapi.core.repository.UserRepository
import com.onedev.ojolapi.exception.OjolException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UserServiceImpl(
    @Autowired
    private val userRepository: UserRepository
) : UserService {
    override fun login(role: String, userLogin: Login.Request): Result<Login.Response> {
        val resultUser = userRepository.getUserByUsername(userLogin.username)
        return resultUser.map {
            val token = JwtConfig.generateToken(it)
            val passwordInDb = it.password
            val passwordInRequest = userLogin.password
            val roleInDb = it.role

            if (passwordInDb == passwordInRequest) {
                if (roleInDb == role) {
                    Login.Response(token)
                } else {
                    throw OjolException("Role Invalid")
                }
            } else {
                throw OjolException("Password Invalid")
            }
        }
    }

    override fun register(user: Register.User): Result<Boolean> {
        return userRepository.insertUser(user)
    }

    override fun getUserById(id: String): Result<Register.User> {
        return userRepository.getUserById(id).map {
            it.password = null
            it
        }
    }

    override fun getUserByUsername(username: String): Result<Register.User> {
        return userRepository.getUserByUsername(username).map {
            it.password = null
            it
        }
    }

    override fun getUserByRole(id: String, role: String): Result<List<Register.User>> {
        val userId = userRepository.getUserById(id)
        return if (userId.isSuccess)
            userRepository.getUserByRole(role)
        else
            throw OjolException("Token Invalid")
    }
}