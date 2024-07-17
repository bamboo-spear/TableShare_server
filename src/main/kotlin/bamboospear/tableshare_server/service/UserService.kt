package bamboospear.tableshare_server.service

import bamboospear.tableshare_server.dto.request.UserNamePatchRequest
import bamboospear.tableshare_server.dto.response.UserResponse
import bamboospear.tableshare_server.repository.UserRepository
import bamboospear.tableshare_server.util.error.CustomError
import bamboospear.tableshare_server.util.error.ErrorState
import org.springframework.stereotype.Service
import java.security.Principal
import java.util.*

@Service
class UserService(val userRepository: UserRepository) {
    fun getMyInfo(principal: Principal): UserResponse {
        val user = userRepository.findUserByUuid(UUID.fromString(principal.name)) ?: throw CustomError(ErrorState.NOT_FOUND_USER)
        return UserResponse(
            uuid = user.uuid.toString(),
            name = user.name,
            socialCredit = user.socialCredit.toString(),
            socialCreditStack = user.socialCreditStack.toString()
        )
    }
    fun patchMyName(request: UserNamePatchRequest, principal: Principal) {
        val user = userRepository.findUserByUuid(UUID.fromString(principal.name)) ?: throw CustomError(ErrorState.NOT_FOUND_USER)
        userRepository.findUserByName(request.name)?.let { throw CustomError(ErrorState.NAME_IS_ALREADY_USED) }
        userRepository.save(user.copy(name = request.name))
    }
}