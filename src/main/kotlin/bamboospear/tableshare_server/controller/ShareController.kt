package bamboospear.tableshare_server.controller

import bamboospear.tableshare_server.dto.request.SharePostRequest
import bamboospear.tableshare_server.dto.request.SharesGetRequest
import bamboospear.tableshare_server.dto.response.ShareInfoGetRequest
import bamboospear.tableshare_server.service.ShareService
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
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.security.Principal

@RestController
@RequestMapping("/share")
class ShareController(val shareService: ShareService) {
    @PostMapping(consumes = ["multipart/form-data"])
    fun postShare(request: SharePostRequest, principal: Principal): ResponseEntity<ResponseFormat<Any>> {
        shareService.postShare(request, principal)
        return ResponseEntity.ok(ResponseFormatBuilder { message = "success" }.noData())
    }
    @GetMapping("/my")
    fun getMyShares(principal: Principal): ResponseEntity<ResponseFormat<List<ShareInfoGetRequest>>> {
        val shares = shareService.getMyShares(principal)
        return ResponseEntity.ok(ResponseFormatBuilder { message = "success" }.build(shares))
    }
    @GetMapping("/{uuid}")
    fun getShareInfo(@PathVariable uuid: String): ResponseEntity<ResponseFormat<ShareInfoGetRequest>> {
        val share = shareService.getShareInfo(uuid)
        return ResponseEntity.ok(ResponseFormatBuilder { message = "success" }.build(share))
    }
    @GetMapping()
    fun getShares(
        @RequestParam lat: Double,
        @RequestParam lng: Double,
        @RequestParam km: Int,
        @RequestParam category: Int
    ): ResponseEntity<ResponseFormat<List<ShareInfoGetRequest>>> {
        val request = SharesGetRequest(lat, lng, km, category)
        val shares = shareService.getShares(request)
        return ResponseEntity.ok(ResponseFormatBuilder { message = "success" }.build(shares))
    }

    @DeleteMapping("/{uuid}")
    fun deleteShare(@PathVariable uuid: String, principal: Principal): ResponseEntity<ResponseFormat<Any>> {
        shareService.deleteShare(uuid, principal)
        return ResponseEntity.ok(ResponseFormatBuilder { message = "success" }.noData())
    }
}