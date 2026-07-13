package com.example.citassalon.presentacion.features.profile.userprofile

import androidx.compose.ui.graphics.Color
import com.example.data.preferences.LoginPreferences
import com.example.data.remote.auth.AuthRepository
import com.example.data.remote.user.UserRepository
import com.example.domain.state.ApiResult
import com.example.domain.state.getContent
import com.example.domain.state.getErrorMessage
import com.example.domain.state.getResultOrNull
import com.example.domain.state.isError
import com.example.domain.state.isSuccess
import javax.inject.Inject

class GetUserInfoUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val loginPreferences: LoginPreferences,
    private val userRepository: UserRepository
) {

    //Todo add use for get random info for this fake api https://randomuser.me/

    suspend fun invoke(): ApiResult<UserProfileUiState> {
        val userResult = authRepository.getUser()
        if (userResult is ApiResult.Error) {
            return ApiResult.Error(userResult.getErrorMessage())
        }
        val user = userResult.getResultOrNull() ?: return ApiResult.Error("User not found")
        val money = loginPreferences.getUserMoney().toString()
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
        val userInfo = UserProfileUiState().copy(
            name = name,
            email = user.email,
            uid = user.uid,
            phone = phone,
            money = money,
            image = image,
            statusColor = getUserSessionStatus()
        )
        return ApiResult.Success(userInfo)
    }

    private fun getUserSessionStatus(): Color {
        val userResult = authRepository.getUser()

        if (userResult.isError()) {
            return Color.Red
        }

        if (userResult.getResultOrNull() == null) {
            return Color.Red
        }

        return Color.Green

    }
}