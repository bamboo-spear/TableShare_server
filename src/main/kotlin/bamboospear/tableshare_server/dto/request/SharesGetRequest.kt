package bamboospear.tableshare_server.dto.request

import bamboospear.tableshare_server.entity.Category

data class SharesGetRequest(
    val lat: Double,
    val lng: Double,
    val km: Int,
    val category: Int
)
