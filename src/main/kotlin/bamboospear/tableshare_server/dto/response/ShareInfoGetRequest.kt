package bamboospear.tableshare_server.dto.response

import java.util.*

data class ShareInfoGetRequest(
    val uuid: UUID = UUID.randomUUID(),
    val sharer: UserResponse,
    val category: String,
    val images: List<ImageResponse>,
    val title: String,
    val description: String,
    val address: String,
    val lat: Double,
    val lng: Double
)
