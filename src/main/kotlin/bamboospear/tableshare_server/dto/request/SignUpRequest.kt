package bamboospear.tableshare_server.dto.request

data class SignUpRequest(
    val name: String,
    val id: String,
    val password: String
)
