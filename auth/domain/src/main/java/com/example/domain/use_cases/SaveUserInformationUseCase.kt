package com.example.domain.use_cases

import com.example.domain.entities.remote.User
import com.example.domain.repository.UserRepository
import com.example.domain.state.ApiResult
import com.example.domain.state.getContent
import com.example.domain.state.isError
import javax.inject.Inject

class SaveUserInformationUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(user: User): ApiResult<Unit> {

        val getUserResult = userRepository.getUser()

        if (getUserResult.isError()) {
            return ApiResult.Error("")
        }

        val userUid = getUserResult.getContent().uid
        if (userUid.isEmpty()) {
            return ApiResult.Error()
        }


        val userInfoResult = userRepository.saveUserInfo(
            userId = userUid,
            user = user
        )

        if (userInfoResult.isError()) {
            return ApiResult.Error("")
        }

        return ApiResult.Success(Unit)
    }
}