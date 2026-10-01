package com.example.domain.usecases

import com.example.domain.UserPreferences
import com.example.domain.repository.AuthRepository
import com.example.domain.repository.UserRepository
import com.example.domain.state.ApiResult
import com.example.domain.state.getContent
import com.example.domain.state.getErrorMessage
import com.example.domain.state.isError
import com.example.domain.state.isSuccess
import com.example.model.state.models.UserRole
import javax.inject.Inject


class LoginUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val userPreferences: UserPreferences,
    private val userRepository: UserRepository
) {

    suspend operator fun invoke(email: String, password: String): ApiResult<UserRole> {

        val loginResult = authRepository.login(email, password)

        if (loginResult.isError()) {
            return ApiResult.Error(loginResult.getErrorMessage())
        }

        val userResult = userRepository.getUser()
        val userRole = if (userResult.isSuccess()) {
            userResult.getContent().role
        } else {
            UserRole.CUSTOMER
        }

        userPreferences.saveUserLogged()
        userPreferences.saveUserEmail(email)


        return ApiResult.Success(userRole)
    }
}