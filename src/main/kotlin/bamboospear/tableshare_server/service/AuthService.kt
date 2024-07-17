package bamboospear.tableshare_server.service

import bamboospear.tableshare_server.dto.request.LoginRequest
import bamboospear.tableshare_server.dto.request.SignUpRequest
import bamboospear.tableshare_server.entity.User
import bamboospear.tableshare_server.repository.UserRepository
import bamboospear.tableshare_server.util.error.CustomError
import bamboospear.tableshare_server.util.error.ErrorState
import bamboospear.tableshare_server.util.jwt.JwtProvider
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class AuthService(val userRepository: UserRepository, val passwordEncoder: BCryptPasswordEncoder, val jwtProvider: JwtProvider) {
    fun signUp(request: SignUpRequest) {
        userRepository.findUserByName(request.name)?.let { throw CustomError(ErrorState.NAME_IS_ALREADY_USED) }
        userRepository.findUserById(request.id)?.let { throw CustomError(ErrorState.ID_IS_ALREADY_USED) }
        userRepository.save(User(
            name = request.name,
            id = request.id,
            password = passwordEncoder.encode(request.password)
        ))
    }
    fun login(request: LoginRequest): String {
        val user = userRepository.findUserById(request.id) ?: throw CustomError(ErrorState.NOT_FOUND_ID)
        if (!passwordEncoder.matches(request.password, user.password)) throw CustomError(ErrorState.WRONG_PASSWORD)
        val accessToken = jwtProvider.createToken(user.uuid)
        return accessToken
    }
}