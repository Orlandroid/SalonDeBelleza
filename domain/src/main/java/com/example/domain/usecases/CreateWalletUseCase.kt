package com.example.domain.usecases

import com.example.domain.repository.WalletRepository
import com.example.domain.wallet.Wallet
import com.example.model.state.ApiResult
import com.example.model.state.getErrorMessage
import com.example.model.state.isError
import com.example.model.state.isSuccess
import com.example.model.state.models.Currency
import javax.inject.Inject
import kotlin.random.Random

class CreateWalletUseCase @Inject constructor(
    private val walletRepository: WalletRepository
) {

    suspend operator fun invoke(userId: String): ApiResult<Unit> {

        val walletResult = walletRepository.getWallet()

        if (walletResult.isSuccess()) return ApiResult.Success(Unit)

        val initialBalance = Random.nextLong(
            from = 2_000,
            until = 10_001
        )

        val wallet = Wallet(
            userId = userId,
            balance = initialBalance,
            currency = Currency.USD,
            createdAt = System.currentTimeMillis()
        )

        val createWalletResult = walletRepository.createWallet(wallet)
        if (createWalletResult.isError()) {
            return ApiResult.Error(createWalletResult.getErrorMessage())
        }
        return ApiResult.Success(Unit)
    }
}