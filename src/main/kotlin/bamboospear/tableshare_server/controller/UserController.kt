package bamboospear.tableshare_server.controller

import bamboospear.tableshare_server.dto.request.UserNamePatchRequest
import bamboospear.tableshare_server.service.UserService
import bamboospear.tableshare_server.util.ResponseFormat
import bamboospear.tableshare_server.util.ResponseFormatBuilder
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.security.Principal

@RestController
class UserController(val userService: UserService) {
    @GetMapping("/MyInfo")
    fun getMyInfo(principal: Principal): ResponseEntity<ResponseFormat<Any>> {
        val result = userService.getMyInfo(principal)
        return ResponseEntity.ok(ResponseFormatBuilder { message = "success" }.build(result))
    }
    @PatchMapping("/name")
    fun patchMyName(@RequestBody @Valid request: UserNamePatchRequest, principal: Principal): ResponseEntity<ResponseFormat<Any>> {
        userService.patchMyName(request, principal)
        return ResponseEntity.ok(ResponseFormatBuilder { message = "success" }.noData())
    }
}