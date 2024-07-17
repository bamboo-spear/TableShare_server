package bamboospear.tableshare_server.dto.response

import bamboospear.tableshare_server.entity.Category
import bamboospear.tableshare_server.entity.Image
import bamboospear.tableshare_server.entity.User
import jakarta.persistence.*
import java.util.*

data class ShareInfoGetRequest(
    val uuid: UUID = UUID.randomUUID(),
    val sharer: UserResponse,
    val category: String,
    val images: List<ImageResponse>,
    val title: String,
    val description: String,
    val lat: Double,
    val lng: Double
)
