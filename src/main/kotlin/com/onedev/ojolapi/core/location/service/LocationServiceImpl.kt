package com.onedev.ojolapi.core.location.service

import com.onedev.ojolapi.core.location.component.LocationComponent
import com.onedev.ojolapi.core.location.entity.Coordinate
import com.onedev.ojolapi.core.location.entity.Location
import com.onedev.ojolapi.core.location.entity.Routes
import com.onedev.ojolapi.utils.Mapper.mapLocationHereToLocation
import com.onedev.ojolapi.utils.Mapper.mapRouteHereToRoutes
import com.onedev.ojolapi.utils.orThrow
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class LocationServiceImpl(
    @Autowired
    private val fetcher: LocationComponent
) : LocationService {
    override fun searchLocation(coordinate: Coordinate, name: String): Result<List<Location>> {
        return fetcher.searchLocation(coordinate, name).map {
            it.mapLocationHereToLocation()
        }
    }

    override fun reserveLocation(coordinate: Coordinate): Result<Location> {
        return fetcher.reverseLocation(coordinate).map {
            it.mapLocationHereToLocation().firstOrNull().orThrow("Location not found")
        }
    }

    override fun getRoutesLocation(transportMode: String, origin: Coordinate, destination: Coordinate): Result<Routes> {
        return fetcher.routePolyline(transportMode, origin, destination).map {
            val route = it.mapRouteHereToRoutes()
            Routes(route)
        }
    }
}