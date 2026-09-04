package com.example.domain.use_cases

import com.example.domain.entities.remote.User
import com.example.domain.repository.AuthRepository
import com.example.domain.repository.UserRepository
import com.example.domain.state.ApiResult
import com.example.domain.state.getContent
import com.example.domain.state.isError
import javax.inject.Inject


class SaveUserInformationUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(user: User): ApiResult<Unit> {

        val getUserResult = authRepository.getUser()

        getUserResult.getContent()?.uid ?: return ApiResult.Error("")

        if (getUserResult.isError()) {
            return ApiResult.Error("")
        }

        val userInfoUseCaseResult = userRepository.saveUserInfo(
            userId = getUserResult.getContent()?.uid.toString(),
            user = user
        )

        if (userInfoUseCaseResult.isError()) {
            return ApiResult.Error("")
        }

        return ApiResult.Success(Unit)
    }
}