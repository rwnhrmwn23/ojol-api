package com.onedev.ojolapi.core.controller

import com.onedev.ojolapi.core.entity.Login
import com.onedev.ojolapi.core.entity.Register
import com.onedev.ojolapi.core.service.UserService
import com.onedev.ojolapi.response.BaseResponse
import com.onedev.ojolapi.utils.toResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/user")
class UserController {

    @Autowired
    private lateinit var userService: UserService

    @GetMapping
    fun getUser(): BaseResponse<Register.User> {
        val userId = SecurityContextHolder.getContext().authentication.principal as String
        return userService.getUserById(userId).toResponse()
    }

    @PostMapping("/login")
    fun login(
        @RequestBody userLogin: Login.Request
    ): BaseResponse<Login.Response> {
        return userService.login(userLogin).toResponse()
    }

    @PostMapping("/register")
    fun register(
        @RequestBody userRequest: Register.Request
    ): BaseResponse<Boolean> {
        return userService.register(userRequest.mapToNewUser()).toResponse()
    }

}