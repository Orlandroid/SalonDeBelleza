package com.example.domain.use_cases

import com.example.domain.entities.UserProfile
import com.example.domain.repository.AuthRepository
import com.example.domain.repository.UserRepository
import com.example.domain.state.ApiResult
import com.example.domain.state.getContent
import com.example.domain.state.getErrorMessage
import com.example.domain.state.getResultOrNull
import com.example.domain.state.isError
import com.example.domain.state.isSuccess
import javax.inject.Inject

class GetUserInfoUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository
) {

    //Todo add use for get random info for this fake api https://randomuser.me/

    suspend fun invoke(): ApiResult<UserProfile> {
        val userResult = authRepository.getUser()
        if (userResult is ApiResult.Error) {
            return ApiResult.Error(userResult.getErrorMessage())
        }
        val user = userResult.getResultOrNull() ?: return ApiResult.Error("User not found")
        val money = userRepository.getUserMoney().toString()
        var image: String? = null
        val imageResult = userRepository.getUserImage()
        if (imageResult.isSuccess()) {
            image = imageResult.getContent()
        }
        val nameAndPhone = userRepository.getNameAndPhone()
        var name = ""
        var phone = ""
        if (nameAndPhone.isSuccess()) {
            name = nameAndPhone.getContent().name
            phone = nameAndPhone.getContent().phone
        }
        val userInfo = UserProfile(
            name = name,
            email = user.email.orEmpty(),
            uid = user.uid,
            phone = phone,
            money = money,
            image = image,
            sessionStatus = getUserSessionStatus()
        )
        return ApiResult.Success(userInfo)
    }

    enum class UserSessionStatus {
        ACTIVE,
        INACTIVE
    }

    private fun getUserSessionStatus(): UserSessionStatus {
        val userResult = authRepository.getUser()

        if (userResult.isError()) {
            return UserSessionStatus.INACTIVE
        }

        if (userResult.getResultOrNull() == null) {
            return UserSessionStatus.INACTIVE
        }

        return UserSessionStatus.ACTIVE

    }
}