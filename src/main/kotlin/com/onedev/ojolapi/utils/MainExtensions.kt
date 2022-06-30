package com.onedev.ojolapi.utils

import com.onedev.ojolapi.exception.OjolException
import com.onedev.ojolapi.response.BaseResponse

inline fun <reified T> T?.toResult(
    message: String = "${T::class.simpleName} is Null"
): Result<T> {
    return if (this != null) {
        Result.success(this)
    } else {
        Result.failure(OjolException(message))
    }
}

fun <T> Result<T>.toResponse(): BaseResponse<T> {
    return if (this.isFailure) {
        throw OjolException(this.exceptionOrNull()?.message ?: "Failure")
    } else {
        BaseResponse.success(this.getOrNull())
    }
}