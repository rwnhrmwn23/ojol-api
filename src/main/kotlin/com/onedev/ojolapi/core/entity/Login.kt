package com.onedev.ojolapi.core.entity

object Login {
    data class Request(
        var username: String,
        var password: String,
    )

    data class Response(
        var token: String = "",
    )
}