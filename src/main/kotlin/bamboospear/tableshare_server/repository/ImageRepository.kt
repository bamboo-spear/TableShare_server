package bamboospear.tableshare_server.repository

import bamboospear.tableshare_server.entity.Image
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface ImageRepository: JpaRepository<Image, UUID> {
}