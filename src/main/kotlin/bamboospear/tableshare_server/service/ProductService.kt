package bamboospear.tableshare_server.service

import bamboospear.tableshare_server.dto.response.ProductInfoResponse
import bamboospear.tableshare_server.entity.Order
import bamboospear.tableshare_server.repository.OrderRepository
import bamboospear.tableshare_server.repository.ProductRepository
import bamboospear.tableshare_server.repository.UserRepository
import bamboospear.tableshare_server.util.error.CustomError
import bamboospear.tableshare_server.util.error.ErrorState
import org.springframework.stereotype.Service
import java.security.Principal
import java.util.UUID

@Service
class ProductService(val productRepository: ProductRepository, val userRepository: UserRepository, val orderRepository: OrderRepository) {
    fun getProducts(): List<ProductInfoResponse> {
        return productRepository.findAll().map { ProductInfoResponse(
            uuid = it.uuid,
            name = it.name,
            description = it.description,
            price = it.price,
            image = it.image
        ) }
    }
    fun postOrder(uuid: String, principal: Principal) {
        val product = productRepository.findProductByUuid(UUID.fromString(uuid)) ?: throw CustomError(ErrorState.NOT_FOUND_PRODUCT)
        val user = userRepository.findUserByUuid(UUID.fromString(principal.name)) ?: throw CustomError(ErrorState.NOT_FOUND_USER)
        if (user.socialCredit < product.price) throw CustomError(ErrorState.YOU_ARE_POOL)
        userRepository.save(user.copy(socialCredit = user.socialCredit - product.price))
        orderRepository.save(Order(
            orderer = user,
            product = product
        ))
    }
}