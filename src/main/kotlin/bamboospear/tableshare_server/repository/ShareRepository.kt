package bamboospear.tableshare_server.repository

import bamboospear.tableshare_server.entity.Share
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.UUID

interface ShareRepository: JpaRepository<Share, UUID> {
    fun findShareByUuid(uuid: UUID): Share?
    @Query("SELECT s FROM Share s LEFT JOIN FETCH s.images LEFT JOIN FETCH s.sharer WHERE (s.lat >= :lat - :km * 0.0113 and s.lat <= :lat + :km * 0.0113) and (s.lng >= :lng - :km * 0.0091 and s.lng <= :lng + :km * 0.0091)")
    fun findSharesByLatAnAndLngAndKm(lat: Double, lng: Double, km: Int): List<Share>
}