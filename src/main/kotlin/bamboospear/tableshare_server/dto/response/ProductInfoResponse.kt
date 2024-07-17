package bamboospear.tableshare_server.dto.response

import bamboospear.tableshare_server.entity.Image
import jakarta.persistence.OneToOne
import java.util.*

data class ProductInfoResponse(
    val uuid: UUID = UUID.randomUUID(),
    val name: String,
    val description: String,
    val price: Long,
    val image: Image
)
