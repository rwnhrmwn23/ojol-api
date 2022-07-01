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
@RequestMapping("/api/v1/customer")
class CustomerController {

    @Autowired
    private lateinit var userService: UserService

    @GetMapping
    fun getCustomer(): BaseResponse<Register.User > {
        val userId = SecurityContextHolder.getContext().authentication.principal as String
        return userService.getUserById(userId).toResponse()
    }

    @GetMapping("/all")
    fun getAllCustomer(): BaseResponse<List<Register.User>> {
        val userId = SecurityContextHolder.getContext().authentication.principal as String
        return userService.getUserByRole(userId, "customer").toResponse()
    }

    @PostMapping("/login")
    fun login(
        @RequestBody userLogin: Login.Request
    ): BaseResponse<Login.Response> {
        return userService.login(role = "customer", userLogin).toResponse()
    }

    @PostMapping("/register")
    fun register(
        @RequestBody userRequest: Register.Request
    ): BaseResponse<Boolean> {
        return userService.register(userRequest.mapToNewUser(role = "customer")).toResponse()
    }

}