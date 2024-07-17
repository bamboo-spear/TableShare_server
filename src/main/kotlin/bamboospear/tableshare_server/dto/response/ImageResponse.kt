package bamboospear.tableshare_server.dto.response

import java.util.*

data class ImageResponse(
    val uuid: UUID = UUID.randomUUID(),
    val url: String
)
