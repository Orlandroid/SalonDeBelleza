package com.example.citassalon.presentacion.features.mappers

import androidx.compose.ui.graphics.Color
import com.example.citassalon.presentacion.features.profile.userprofile.UserProfileUiState
import com.example.domain.entities.UserProfile
import com.example.domain.use_cases.GetUserInfoUseCase

fun UserProfile.toUiState(): UserProfileUiState {
    return UserProfileUiState(
        name = name,
        email = email,
        uid = uid,
        phone = phone,
        money = money,
        image = image,
        statusColor = when (sessionStatus) {
            GetUserInfoUseCase.UserSessionStatus.ACTIVE -> Color.Green
            GetUserInfoUseCase.UserSessionStatus.INACTIVE -> Color.Red
        }
    )
}