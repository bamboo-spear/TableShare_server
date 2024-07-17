package bamboospear.tableshare_server.controller

import bamboospear.tableshare_server.service.GeocodingService
import org.springframework.data.repository.query.Param
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class TestController(val geocodingService: GeocodingService) {
    @GetMapping("/test")
    fun test(@Param(value = "adress") adress: String): String {
        val location = geocodingService.getLatLngFromAddress(adress) ?: throw Error()
        return "${location.first}  ---   ${location.second}"
    }
}