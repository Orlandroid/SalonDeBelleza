package com.example.domain.usecases

import com.example.domain.repository.WalletRepository
import com.example.domain.wallet.Balance
import com.example.model.state.ApiResult
import com.example.model.state.getContent
import com.example.model.state.isError
import javax.inject.Inject

class GetWalletUseCase @Inject constructor(
    private val walletRepository: WalletRepository
) {

    suspend operator fun invoke(): ApiResult<Balance> {

        val walletResult = walletRepository.getWallet()
        if (walletResult.isError()) return ApiResult.Error()
        val balance = walletResult.getContent()
        return ApiResult.Success(
            Balance(
                userName = "",
                balance = balance.balance,
                currency = balance.currency,
                createdAtMillis = balance.createdAt,
                userId = balance.userId
            )
        )
    }
}