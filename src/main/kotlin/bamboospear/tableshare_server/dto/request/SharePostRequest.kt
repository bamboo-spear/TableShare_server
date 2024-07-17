package bamboospear.tableshare_server.dto.request

import org.springframework.web.multipart.MultipartFile

data class SharePostRequest(
    val category: Int,
    val images: List<MultipartFile>,
    val title: String,
    val description: String,
    val address: String
)