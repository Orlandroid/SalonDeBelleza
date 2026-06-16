package com.example.citassalon.presentacion.features.profile.userprofile

import androidx.compose.ui.graphics.Color
import com.example.data.preferences.LoginPreferences
import com.example.data.remote.auth.AuthRepository
import com.example.domain.state.ApiResult
import com.example.domain.state.getErrorMessage
import com.example.domain.state.getResultOrNull
import com.example.domain.state.isError
import javax.inject.Inject

class GetUserInfoUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val loginPreferences: LoginPreferences,
    private val getUserImage: GetUserImageUseCase
) {

    //Todo add use for get random info for this fake api https://randomuser.me/

    suspend fun invoke(): ApiResult<UserProfileUiState> {
        val userResult = authRepository.getUser()
        if (userResult is ApiResult.Error) {
            return ApiResult.Error(userResult.getErrorMessage())
        }
        val user = userResult.getResultOrNull() ?: return ApiResult.Error("User not found")
        val money = loginPreferences.getUserMoney().toString()
        val image = getUserImage.invoke().getResultOrNull()
        val userInfo = UserProfileUiState().copy(
            name = user.displayName,
            email = user.email,
            uid = user.uid,
            phone = user.phoneNumber,
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
