package bamboospear.tableshare_server.controller

import bamboospear.tableshare_server.service.WantService
import bamboospear.tableshare_server.util.ResponseFormat
import bamboospear.tableshare_server.util.ResponseFormatBuilder
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.security.Principal

@RestController
@RequestMapping("/share")
class WantController(val wantService: WantService) {
    @GetMapping("/{uuid}/want")
    fun getWants(@PathVariable uuid: String, principal: Principal): ResponseEntity<ResponseFormat<Any>> {
        wantService.getWants(uuid, principal)
        return ResponseEntity.ok(ResponseFormatBuilder { message = "success" }.noData())
    }
    @PostMapping("/{uuid}/want")
    fun postWant(@PathVariable uuid: String, principal: Principal): ResponseEntity<ResponseFormat<Any>> {
        wantService.postWant(uuid, principal)
        return ResponseEntity.ok(ResponseFormatBuilder { message = "success" }.noData())
    }
    @PostMapping("/{uuid}/want/{wisherUUID}")
    fun postAceptWant(@PathVariable uuid: String, @PathVariable wisherUUID: String, principal: Principal): ResponseEntity<ResponseFormat<Any>> {
        wantService.postAceptWant(uuid, wisherUUID, principal)
        return ResponseEntity.ok(ResponseFormatBuilder { message = "success" }.noData())
    }
    @DeleteMapping("/{uuid}/want")
    fun deleteWant(@PathVariable uuid: String, principal: Principal): ResponseEntity<ResponseFormat<Any>> {
        wantService.deleteWant(uuid, principal)
        return ResponseEntity.ok(ResponseFormatBuilder { message = "success" }.noData())
    }
}