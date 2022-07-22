package com.onedev.ojolapi.utils

import com.onedev.ojolapi.core.location.entity.Coordinate
import com.onedev.ojolapi.exception.OjolException
import com.onedev.ojolapi.response.BaseResponse

inline fun <reified T> T?.orThrow(
    message: String = "${T::class.simpleName} is null"
): T {
    return this ?: throw OjolException(message)
}

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

fun String.convertToCoordinate(): Coordinate {
    val coordinateString = split(",")
    val lat = coordinateString[0].toDoubleOrNull() ?: 0.0
    val lon = coordinateString[1].toDoubleOrNull() ?: 0.0
    return Coordinate(lat, lon)
}