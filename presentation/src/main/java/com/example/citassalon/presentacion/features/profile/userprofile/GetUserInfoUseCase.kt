package com.example.citassalon.presentacion.features.profile.userprofile

import androidx.compose.ui.graphics.Color
import com.example.citassalon.presentacion.features.base.BaseScreenState
import com.example.data.Repository
import com.example.data.preferences.LoginPreferences
import com.example.domain.perfil.UserProfileResponse
import com.example.domain.state.ApiResult
import com.example.domain.state.getResultOrNull
import javax.inject.Inject

class GetUserInfoUseCase @Inject constructor(
    private val repository: Repository,
    private val loginPreferences: LoginPreferences,
    private val getUserImage: GetUserImageUseCase
) {

    //Todo add use for get random info for this fake api https://randomuser.me/

    suspend fun invoke(): ApiResult<UserProfileUiState> {
        val user = repository.getUser() ?: return ApiResult.Error(null)
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
        if (repository.getUser() == null) {
            return Color.Red
        }
        return Color.Green
    }
}
