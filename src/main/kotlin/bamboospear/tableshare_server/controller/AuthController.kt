package bamboospear.tableshare_server.controller

import bamboospear.tableshare_server.dto.request.LoginRequest
import bamboospear.tableshare_server.dto.request.SignUpRequest
import bamboospear.tableshare_server.service.AuthService
import bamboospear.tableshare_server.util.ResponseFormat
import bamboospear.tableshare_server.util.ResponseFormatBuilder
import jakarta.servlet.http.HttpServletRequest
import jakarta.validation.Valid
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class AuthController(val authService: AuthService) {
    val log = LoggerFactory.getLogger(javaClass)
    @PostMapping("/sign-up")
    fun signUp(@RequestBody @Valid request: SignUpRequest, httpServletRequest: HttpServletRequest): ResponseEntity<ResponseFormat<Any>> {
        authService.signUp(request)
        return ResponseEntity.ok(ResponseFormatBuilder { message = "success" }.noData())
    }

    @PostMapping("/login")
    fun login(@RequestBody @Valid request: LoginRequest): ResponseEntity<ResponseFormat<String>> {
        val result = authService.login(request)
        return ResponseEntity.ok(ResponseFormatBuilder { message = "success" }.build(result))
    }
}