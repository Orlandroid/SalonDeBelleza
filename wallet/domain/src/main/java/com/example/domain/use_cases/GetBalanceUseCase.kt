package com.example.domain.use_cases

import com.example.domain.repository.UserRepository
import com.example.domain.repository.WalletRepository
import com.example.domain.state.ApiResult
import com.example.domain.state.getContent
import com.example.domain.state.getErrorMessage
import com.example.domain.state.isError
import com.example.domain.wallet.Balance
import javax.inject.Inject

class GetBalanceUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val walletRepository: WalletRepository
) {

    suspend operator fun invoke(): ApiResult<Balance> {

        val userResult = userRepository.getNameAndPhone()
        if (userResult.isError()) {
            return ApiResult.Error(
                userResult.getErrorMessage()
            )
        }

        val user = userResult.getContent()

        val walletResult = walletRepository.getWallet()
        if (walletResult.isError()) {
            return ApiResult.Error(
                walletResult.getErrorMessage()
            )
        }

        val wallet = walletResult.getContent()

        return ApiResult.Success(
            Balance(
                userId = wallet.userId,
                userName = user.name,
                balance = wallet.balance,
                currency = wallet.currency,
                createdAtMillis = wallet.createdAt
            )
        )
    }
}