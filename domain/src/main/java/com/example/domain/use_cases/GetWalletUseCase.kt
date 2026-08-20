package com.example.domain.use_cases

import com.example.domain.state.ApiResult
import com.example.domain.state.getContent
import com.example.domain.state.isError
import com.example.domain.wallet.Balance
import com.example.domain.wallet.WalletRepository
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