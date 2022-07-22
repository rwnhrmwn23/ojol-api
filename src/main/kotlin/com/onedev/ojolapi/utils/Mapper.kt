package com.onedev.ojolapi.utils

import com.onedev.ojolapi.location.util.PolylineEncoderDecoder
import com.onedev.ojolapi.core.location.entity.Coordinate
import com.onedev.ojolapi.core.location.entity.Location
import com.onedev.ojolapi.core.location.entity.HereLocationResponse
import com.onedev.ojolapi.core.location.entity.HereRouteLocationResponse

object Mapper {
    fun HereLocationResponse.mapLocationHereToLocation(): List<Location> {
        return items?.map {
            val address = Location.Address(
                city = it?.address?.city.orEmpty(),
                country = it?.address?.countryName.orEmpty(),
                district = it?.address?.district.orEmpty()
            )
            Location(
                name = it?.title.orEmpty(),
                address = address,
                coordinate = Coordinate(it?.position?.lat ?: 0.0, it?.position?.lng ?: 0.0),
            )
        }.orEmpty()
    }

    fun HereRouteLocationResponse.mapRouteHereToRoutes(): List<Coordinate> {
        val polylineString = routes
            ?.firstOrNull()
            ?.sections
            ?.firstOrNull()
            ?.polyline
            .orEmpty()
        return PolylineEncoderDecoder.decode(polylineString)
            .map {
                Coordinate(it.lat, it.lng)
            }
    }
}