package com.onedev.ojolapi.core.location.component

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.exc.InvalidDefinitionException
import com.onedev.ojolapi.core.location.entity.Coordinate
import com.onedev.ojolapi.core.location.entity.HereLocationResponse
import com.onedev.ojolapi.core.location.entity.HereRouteLocationResponse
import okhttp3.OkHttpClient
import okhttp3.Request
import org.bson.json.JsonParseException
import org.springframework.stereotype.Component

@Component
class LocationComponent {

    private val client = OkHttpClient()
    private inline fun <reified T> getHttp(url: String): Result<T> {
        return try {
            val request = Request.Builder()
                .url(url)
                .build()
            val response = client.newCall(request).execute()
            val body = response.body
            val bodyString = body?.string()
            if (response.isSuccessful) {
                val data = ObjectMapper().readValue(bodyString, T::class.java)
                Result.success(data)
            } else {
                val throwable = IllegalArgumentException(response.message)
                Result.failure(throwable)
            }
        } catch (e: JsonParseException) {
            Result.failure(e)
        } catch (e: InvalidDefinitionException) {
            Result.failure(e)

        } catch (e: Throwable) {
            Result.failure(e)
        }
    }

    fun searchLocation(coordinate: Coordinate, name: String): Result<HereLocationResponse> {
        val coordinateString = "${coordinate.latitude},${coordinate.longitude}"
        val url = SEARCH_LOC
            .replace(COORDINATE, coordinateString)
            .replace(NAME, name)
        return getHttp(url)
    }

    fun reverseLocation(coordinate: Coordinate): Result<HereLocationResponse> {
        val coordinateString = "${coordinate.latitude},${coordinate.longitude}"
        val url = REVERSE_LOC
            .replace(COORDINATE, coordinateString)
        return getHttp(url)
    }

    fun routePolyline(
        transportMode: String,
        origin: Coordinate,
        destination: Coordinate
    ): Result<HereRouteLocationResponse> {
        val coordinateOriginString = "${origin.latitude},${origin.longitude}"
        val coordinateDestinationString = "${destination.latitude},${destination.longitude}"
        val url = ROUTE_POLYLINE_LOC
            .replace(TRANSPORT_MODE, transportMode)
            .replace(COORDINATE_ORIGIN, coordinateOriginString)
            .replace(COORDINATE_DESTINATION, coordinateDestinationString)
        return getHttp(url)
    }

    companion object {
        const val NAME = "{{name}}"
        const val COORDINATE = "{{coordinate}}"
        const val COORDINATE_ORIGIN = "{{coordinate_origin}}"
        const val COORDINATE_DESTINATION = "{{coordinate_destination}}"
        const val TRANSPORT_MODE = "{{transport_mode}}"

        const val SEARCH_LOC =
            "https://discover.search.hereapi.com/v1/discover?at={{coordinate}}&q={{name}}&apiKey=-qt5VBnZCk8c-EMOggqdu5v3soZvbhbKhWO92d4n3dE"
        const val REVERSE_LOC =
            "https://revgeocode.search.hereapi.com/v1/revgeocode?at={{coordinate}}&lang=en-US&apiKey=-qt5VBnZCk8c-EMOggqdu5v3soZvbhbKhWO92d4n3dE"
        const val ROUTE_POLYLINE_LOC =
            "https://router.hereapi.com/v8/routes?transportMode={{transport_mode}}&origin={{coordinate_origin}}&destination={{coordinate_destination}}&return=polyline&apiKey=-qt5VBnZCk8c-EMOggqdu5v3soZvbhbKhWO92d4n3dE"
    }
}