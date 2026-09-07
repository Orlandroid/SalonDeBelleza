package com.example.domain.use_cases

import com.example.domain.repository.AuthRepository
import com.example.domain.UserPreferences
import com.example.domain.state.ApiResult
import com.example.domain.state.getErrorMessage
import com.example.domain.state.isError
import javax.inject.Inject


class LoginUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val userPreferences: UserPreferences
) {

    suspend operator fun invoke(email: String, password: String): ApiResult<Unit> {

        val loginResult = authRepository.login(email, password)

        if (loginResult.isError()) {
            return ApiResult.Error(loginResult.getErrorMessage())
        }

        userPreferences.saveUserLogged()
        userPreferences.saveUserEmail(email)


        return ApiResult.Success(Unit)
    }
}