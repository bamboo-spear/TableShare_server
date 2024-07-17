package bamboospear.tableshare_server.service

import bamboospear.tableshare_server.util.GeocodingResponse
import bamboospear.tableshare_server.util.LocationData
import okhttp3.OkHttpClient
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

@Service
class GeocodingService(
) {
    @Value("\${google.maps-api-key}")
    private lateinit var apiKey: String

    fun getLatLngFromAddress(address: String): Pair<Double, Double>? {
        val uri = UriComponentsBuilder.fromUriString("https://maps.googleapis.com/maps/api/geocode/json")
            .queryParam("address", address)
            .queryParam("key", apiKey)
            .build()
            .toUri()

        val response = RestTemplate().getForObject(uri, GoogleGeocodingResponse::class.java)
        return response?.let {
            val location = it.results.firstOrNull()?.geometry?.location
            Pair(location?.lat ?: 0.0, location?.lng ?: 0.0)
        }
    }
}

data class GoogleGeocodingResponse(
    val results: List<GeocodingResult>,
    val status: String
)

data class GeocodingResult(
    val geometry: Geometry
)

data class Geometry(
    val location: Location
)

data class Location(
    val lat: Double,
    val lng: Double
)