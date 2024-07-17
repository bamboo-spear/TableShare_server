package bamboospear.tableshare_server.repository

import bamboospear.tableshare_server.entity.Share
import bamboospear.tableshare_server.entity.User
import bamboospear.tableshare_server.entity.Want
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface WantRepository: JpaRepository<Want, UUID> {
    fun findWantByShareAndWisher(share: Share, wisher: User): Want?
    fun findWantsByShare(share: Share): List<Want>
}