package com.onedev.ojolapi.core.location.controller

import com.onedev.ojolapi.core.location.entity.Location
import com.onedev.ojolapi.core.location.entity.Routes
import com.onedev.ojolapi.core.location.service.LocationService
import com.onedev.ojolapi.response.BaseResponse
import com.onedev.ojolapi.utils.convertToCoordinate
import com.onedev.ojolapi.utils.toResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/location")
class LocationController {
    @Autowired
    private lateinit var locationService: LocationService

    @GetMapping("/search")
    fun searchLocation(
        @RequestParam coordinate: String,
        @RequestParam name: String
    ): BaseResponse<List<Location>> {
        val coordinateString = coordinate.convertToCoordinate()
        return locationService.searchLocation(coordinateString, name).toResponse()
    }

    @GetMapping("/reverse")
    fun reverseLocation(
        @RequestParam coordinate: String
    ): BaseResponse<Location> {
        val coordinateString = coordinate.convertToCoordinate()
        return locationService.reserveLocation(coordinateString).toResponse()
    }

    @GetMapping("/route")
    fun routeLocation(
        @RequestParam transportMode: String,
        @RequestParam origin: String,
        @RequestParam destination: String
    ): BaseResponse<Routes> {
        val coordinateOriginString = origin.convertToCoordinate()
        val coordinateDestinationString = destination.convertToCoordinate()

        return locationService.getRoutesLocation(
            transportMode,
            coordinateOriginString,
            coordinateDestinationString
        ).toResponse()
    }
}