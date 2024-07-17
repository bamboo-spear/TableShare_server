package bamboospear.tableshare_server.service

import bamboospear.tableshare_server.dto.request.SharePostRequest
import bamboospear.tableshare_server.dto.response.ImageResponse
import bamboospear.tableshare_server.dto.response.ShareInfoGetRequest
import bamboospear.tableshare_server.dto.response.UserResponse
import bamboospear.tableshare_server.entity.Category
import bamboospear.tableshare_server.entity.Share
import bamboospear.tableshare_server.repository.ShareRepository
import bamboospear.tableshare_server.repository.UserRepository
import bamboospear.tableshare_server.util.error.CustomError
import bamboospear.tableshare_server.util.error.ErrorState
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import java.security.Principal
import java.util.UUID

@Service
class ShareService(val shareRepository: ShareRepository, val userRepository: UserRepository, val s3UploadService: S3UploadService, val geocodingService: GeocodingService) {
    fun postShare(request: SharePostRequest, principal: Principal) {
        val user = userRepository.findUserByUuid(UUID.fromString(principal.name)) ?: throw CustomError(ErrorState.NOT_FOUND_USER)
        val location = geocodingService.getLatLngFromAddress(request.adress)
        shareRepository.save(Share(
            sharer = user,
            category = Category.fromInt(request.category)!!, // todo validate
            images = s3UploadService.saveImages(request.images, "share_image"),
            title = request.title,
            description = request.description,
            lat = location!!.first,
            lng = location!!.second
        ))
    }
    fun getShareInfo(uuid: String): ShareInfoGetRequest {
        val share = shareRepository.findShareByUuid(UUID.fromString(uuid)) ?: throw CustomError(ErrorState.NOT_FOUND_SHARE)
        return ShareInfoGetRequest(
            uuid = share.uuid,
            sharer = UserResponse(
                uuid = share.sharer.uuid.toString(),
                name = share.sharer.name,
                socialCredit = share.sharer.socialCredit.toString()
            ),
            category = share.category.toString(),
            images = share.images.map { ImageResponse(
                uuid = it.uuid,
                url = it.url
            ) },
            title = share.title,
            description = share.description,
            lat = share.lat,
            lng = share.lng
        )
    }
    fun getShares(): List<ShareInfoGetRequest> {
        val shares = shareRepository.findSharesByLatAnAndLngAndKm()
    }
}