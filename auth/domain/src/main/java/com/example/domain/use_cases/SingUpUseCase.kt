package com.example.domain.use_cases

import com.example.domain.repository.AuthRepository
import com.example.domain.state.ApiResult
import com.example.domain.state.getContent
import com.example.domain.state.isError
import javax.inject.Inject


class SingUpUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val createWalletUseCase: CreateWalletUseCase,
) {

    suspend operator fun invoke(
        email: String,
        password: String
    ): ApiResult<Unit> {

        val authResult = authRepository.register(email, password)

        if (authResult.isError()) {
            return ApiResult.Error()
        }

        val userUid = authResult
            .getContent()
            .user
            ?.uid
            ?: return ApiResult.Error()

        val walletResult = createWalletUseCase(userUid)

        if (walletResult.isError()) {
            return ApiResult.Error()
        }

        return ApiResult.Success(Unit)
    }
}

