package com.example.profile.mappers

import androidx.compose.ui.graphics.Color
import com.example.domain.UserSessionStatus
import com.example.domain.entities.UserProfile
import com.example.profile.userprofile.UserProfileUiState

fun UserProfile.toUiState(): UserProfileUiState {
    return UserProfileUiState(
        name = name,
        email = email,
        uid = uid,
        phone = phone,
        money = money,
        image = image,
        statusColor = when (sessionStatus) {
            UserSessionStatus.ACTIVE -> Color.Green
            UserSessionStatus.INACTIVE -> Color.Red
        }
    )
}