package bamboospear.tableshare_server.service

import bamboospear.tableshare_server.dto.request.SharePostRequest
import bamboospear.tableshare_server.dto.request.SharesGetRequest
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
            images = s3UploadService.saveImages(request.images, ""),
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
    fun getShares(request: SharesGetRequest): List<ShareInfoGetRequest> {
        val shares = shareRepository.findSharesByLatAnAndLngAndKm(request.lat, request.lng, request.km, Category.fromInt(request.category) ?: throw CustomError(ErrorState.WRONG_CATEGORY))
        return shares.map { ShareInfoGetRequest(
            uuid = it.uuid,
            sharer = UserResponse(
                uuid = it.sharer.uuid.toString(),
                name = it.sharer.name,
                socialCredit = it.sharer.socialCredit.toString()
            ),
            category = it.category.toString(),
            images = it.images.map { ImageResponse(
                uuid = it.uuid,
                url = it.url
            ) },
            title = it.title,
            description = it.description,
            lat = it.lat,
            lng = it.lng
        )
    }}

    fun deleteShare(uuid: String, username: String?) {
        val share = shareRepository.findShareByUuid(UUID.fromString(uuid)) ?: throw CustomError(ErrorState.NOT_FOUND_SHARE)
        if (username != null) {
            val user = userRepository.findUserByName(username) ?: throw CustomError(ErrorState.NOT_FOUND_USER)
            userRepository.save(user.copy(socialCredit = user.socialCredit + 10))
        }
        shareRepository.delete(share)
    }
}