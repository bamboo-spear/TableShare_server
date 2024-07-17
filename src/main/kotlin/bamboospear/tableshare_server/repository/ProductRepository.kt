package bamboospear.tableshare_server.repository

import bamboospear.tableshare_server.entity.Product
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface ProductRepository: JpaRepository<Product, UUID> {
    fun findProductByUuid(uuid: UUID): Product?
}