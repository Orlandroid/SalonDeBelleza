package com.example.domain.use_cases

import com.example.domain.state.isError
import com.example.domain.wallet.Currency
import com.example.domain.wallet.Wallet
import com.example.domain.wallet.WalletRepository
import javax.inject.Inject
import kotlin.random.Random

class CreateWalletUseCase @Inject constructor(
    private val walletRepository: WalletRepository
) {

    suspend operator fun invoke(userId: String) {

        val walletResult = walletRepository.getWallet(userId)

        if (walletResult.isError()) return

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

        walletRepository.createWallet(wallet)
    }
}