package bamboospear.tableshare_server.util

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class GeocodingResponse(
    @JsonProperty("results") val results: List<Result>,
    @JsonProperty("status") val status: String
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class Result(
    @JsonProperty("geometry") val geometry: Geometry
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class Geometry(
    @JsonProperty("location") val location: LocationData
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class LocationData(
    @JsonProperty("lat") val lat: Double,
    @JsonProperty("lng") val lng: Double
)
