package com.onedev.ojolapi.core.location.service

import com.onedev.ojolapi.core.location.entity.Coordinate
import com.onedev.ojolapi.core.location.entity.Location
import com.onedev.ojolapi.core.location.entity.Routes

interface LocationService {
    fun searchLocation(coordinate: Coordinate, name: String): Result<List<Location>>
    fun reserveLocation(coordinate: Coordinate): Result<Location>
    fun getRoutesLocation(transportMode: String, origin: Coordinate, destination: Coordinate): Result<Routes>
}