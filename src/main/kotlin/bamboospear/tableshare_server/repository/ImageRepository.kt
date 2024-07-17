package bamboospear.tableshare_server.repository

import bamboospear.tableshare_server.entity.Image
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface ImageRepository: JpaRepository<Image, UUID> {
}