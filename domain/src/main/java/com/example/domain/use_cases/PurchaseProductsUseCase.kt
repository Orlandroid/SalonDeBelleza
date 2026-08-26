package com.example.domain.use_cases

import com.example.domain.state.ApiResult
import com.example.domain.state.getContent
import com.example.domain.state.isError
import com.example.domain.transaction.Transaction
import com.example.domain.transaction.TransactionRepository
import com.example.domain.transaction.TransactionType
import com.example.domain.wallet.WalletRepository
import java.util.UUID
import javax.inject.Inject

class PurchaseProductsUseCase @Inject constructor(
    private val walletRepository: WalletRepository,
    private val transactionRepository: TransactionRepository,
    private val getWalletUseCase: GetWalletUseCase,
) {
    suspend operator fun invoke(
        amount: Long,
        transactionType: TransactionType,
        description: String
    ): ApiResult<Unit> {
        val balanceResult = getWalletUseCase.invoke()
        if (balanceResult.isError()) {
            return ApiResult.Error("Unable to make the purchase")
        }
        val currentBalance = balanceResult.getContent().balance
        if (currentBalance < amount) {
            return ApiResult.Error("Insufficient balance")
        }
        val newBalance = currentBalance - amount
        val updateResult = walletRepository.updateBalance(newBalance)
        if (updateResult.isError()) {
            return ApiResult.Error("Unable to update balance")
        }
        //Todo migrate to atomic multi-location update firebase
        val transactionResult = transactionRepository.createTransaction(
            transaction = Transaction(
                id = UUID.randomUUID().toString(),
                amount = amount,
                transactionType = transactionType,
                description = description,
                createdAt = System.currentTimeMillis()
            )
        )
        if (transactionResult.isError()) {
            return ApiResult.Error("Creation of transaction fail")
        }

        return ApiResult.Success(Unit)
    }
}