package bamboospear.tableshare_server.controller

import bamboospear.tableshare_server.service.UserService
import bamboospear.tableshare_server.util.ResponseFormat
import bamboospear.tableshare_server.util.ResponseFormatBuilder
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import java.security.Principal

@RestController
class UserController(val userService: UserService) {
    @GetMapping("/MyInfo")
    fun getMyInfo(principal: Principal): ResponseEntity<ResponseFormat<Any>> {
        val result = userService.getMyInfo(principal)
        return ResponseEntity.ok(ResponseFormatBuilder { message = "success" }.build(result))
    }
}