package com.example.domain.use_cases

import com.example.domain.repository.WalletRepository
import com.example.domain.state.ApiResult
import com.example.domain.state.getContent
import com.example.domain.state.isError
import com.example.domain.transaction.Transaction
import com.example.domain.transaction.TransactionRepository
import com.example.domain.transaction.TransactionType
import java.util.UUID
import javax.inject.Inject

class RefundAppointmentUseCase @Inject constructor(
    private val walletRepository: WalletRepository,
    private val transactionRepository: TransactionRepository
) {
    suspend operator fun invoke(
        userId: String,
        refundAmount: Double,
        description: String
    ): ApiResult<Unit> {

        val walletResult = walletRepository.getWallet()
        if (walletResult.isError()) return ApiResult.Error("Wallet not found")

        val currentWallet = walletResult.getContent()
        val refundAmountLong = refundAmount.toLong()
        val newBalance = currentWallet.balance + refundAmountLong


        val updateResult = walletRepository.updateBalance(newBalance)
        if (updateResult.isError()) return ApiResult.Error("Failed to update wallet balance")


        val transaction = Transaction(
            id = UUID.randomUUID().toString(),
            amount = refundAmountLong,
            transactionType = TransactionType.REFUND,
            description = description,
            createdAt = System.currentTimeMillis()
        )
        transactionRepository.createTransaction(transaction)

        return ApiResult.Success(Unit)
    }
}