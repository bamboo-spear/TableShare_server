package bamboospear.tableshare_server.controller

import bamboospear.tableshare_server.dto.response.ProductInfoResponse
import bamboospear.tableshare_server.service.ProductService
import bamboospear.tableshare_server.util.ResponseFormat
import bamboospear.tableshare_server.util.ResponseFormatBuilder
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.security.Principal

@RestController
@RequestMapping("/product")
class ProductController(val productService: ProductService) {
    @GetMapping()
    fun getProducts(): ResponseEntity<ResponseFormat<List<ProductInfoResponse>>> {
        val products = productService.getProducts()
        return ResponseEntity.ok(ResponseFormatBuilder { message = "success" }.build(products))
    }
    @PostMapping("/{uuid}/order")
    fun postOrder(@PathVariable uuid: String, principal: Principal): ResponseEntity<ResponseFormat<Any>> {
        productService.postOrder(uuid, principal)
        return ResponseEntity.ok(ResponseFormatBuilder { message = "success" }.noData())
    }
}