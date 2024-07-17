package bamboospear.tableshare_server.service

import bamboospear.tableshare_server.dto.response.UserResponse
import bamboospear.tableshare_server.entity.Want
import bamboospear.tableshare_server.repository.ShareRepository
import bamboospear.tableshare_server.repository.UserRepository
import bamboospear.tableshare_server.repository.WantRepository
import bamboospear.tableshare_server.util.error.CustomError
import bamboospear.tableshare_server.util.error.ErrorState
import org.springframework.stereotype.Service
import java.security.Principal
import java.util.*

@Service
class WantService(val wantRepository: WantRepository, val shareRepository: ShareRepository, val userRepository: UserRepository) {
    fun getWants(uuid: String, principal: Principal): List<UserResponse>{
        val share = shareRepository.findShareByUuid(UUID.fromString(uuid)) ?: throw CustomError(ErrorState.NOT_FOUND_SHARE)
        val user = userRepository.findUserByUuid(UUID.fromString(principal.name))
        if (share.sharer != user) throw CustomError(ErrorState.YOUR_NOT_OWNER)
        val wants = wantRepository.findWantsByShare(share)
        return wants.map { UserResponse(
            uuid = it.wisher.uuid.toString(),
            name = it.wisher.name,
            socialCredit = it.wisher.socialCredit.toString(),
            socialCreditStack = it.wisher.socialCreditStack.toString()
        ) }
    }
    fun postWant(uuid: String, principal: Principal) {
        val share = shareRepository.findShareByUuid(UUID.fromString(uuid)) ?: throw CustomError(ErrorState.NOT_FOUND_SHARE)
        val user = userRepository.findUserByUuid(UUID.fromString(principal.name)) ?: throw CustomError(ErrorState.NOT_FOUND_USER)
        wantRepository.findWantByShareAndWisher(share, user)?.let { throw CustomError(ErrorState.WANT_IS_ALREADY_EXISTED) }
        userRepository.save(share.sharer.copy(socialCredit = share.sharer.socialCredit + 5, socialCreditStack = share.sharer.socialCredit + 5))
        wantRepository.save(Want(
            share = share,
            wisher = user
        ))
    }
    fun postAceptWant(uuid: String, wisherUUID: String, principal: Principal) {
        val share = shareRepository.findShareByUuid(UUID.fromString(uuid)) ?: throw CustomError(ErrorState.NOT_FOUND_SHARE)
        val user = userRepository.findUserByUuid(UUID.fromString(principal.name)) ?: throw CustomError(ErrorState.NOT_FOUND_USER)
        if (share.sharer != user) throw CustomError(ErrorState.YOUR_NOT_OWNER)
        val wisher = userRepository.findUserByUuid(UUID.fromString(wisherUUID)) ?: throw CustomError(ErrorState.NOT_FOUND_USER)
        val want = wantRepository.findWantByShareAndWisher(share, wisher) ?: throw CustomError(ErrorState.NOT_FOUND_WANT)
        userRepository.save(wisher.copy(socialCredit = share.sharer.socialCredit + 5, socialCreditStack = share.sharer.socialCredit + 5))
        wantRepository.delete(want)
    }

    fun deleteWant(uuid: String, principal: Principal) {
        val share = shareRepository.findShareByUuid(UUID.fromString(uuid)) ?: throw CustomError(ErrorState.NOT_FOUND_SHARE)
        val user = userRepository.findUserByUuid(UUID.fromString(principal.name)) ?: throw CustomError(ErrorState.NOT_FOUND_USER)
        val want = wantRepository.findWantByShareAndWisher(share, user) ?: throw CustomError(ErrorState.NOT_FOUND_WANT)
        wantRepository.delete(want)
    }
}