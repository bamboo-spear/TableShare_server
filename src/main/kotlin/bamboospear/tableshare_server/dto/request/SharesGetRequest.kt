package bamboospear.tableshare_server.dto.request

data class SharesGetRequest(
    val lat: Double,
    val lng: Double,
    val km: Int
)
