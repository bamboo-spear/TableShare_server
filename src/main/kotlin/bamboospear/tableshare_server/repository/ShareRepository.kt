package bamboospear.tableshare_server.repository

import bamboospear.tableshare_server.entity.Category
import bamboospear.tableshare_server.entity.Share
import bamboospear.tableshare_server.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface ShareRepository: JpaRepository<Share, UUID> {
    fun findShareByUuid(uuid: UUID): Share?

    @Query("SELECT DISTINCT s FROM Share s LEFT JOIN FETCH s.images LEFT JOIN FETCH s.sharer WHERE (s.lat >= :lat - :km * 0.0113 and s.lat <= :lat + :km * 0.0113) and (s.lng >= :lng - :km * 0.0091 and s.lng <= :lng + :km * 0.0091) and s.category = :category")
    fun findSharesByLatAnAndLngAndKm(lat: Double, lng: Double, km: Int, category: Category): List<Share>
    fun findSharesBySharer(sharer: User): List<Share>
}