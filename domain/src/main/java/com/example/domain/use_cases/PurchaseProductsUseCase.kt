package com.example.domain.use_cases

import com.example.domain.entities.remote.products.Product
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
        products: List<Product>,
        description: String
    ): ApiResult<Unit> {
        val total = calculateTotalOfProducts(products)
        val balanceResult = getWalletUseCase.invoke()
        if (balanceResult.isError()) {
            return ApiResult.Error("Unable to make the purchase")
        }
        val currentBalance = balanceResult.getContent().balance
        if (currentBalance < total) {
            return ApiResult.Error("Insufficient balance")
        }
        val newBalance = currentBalance - total
        val updateResult = walletRepository.updateBalance(newBalance)
        if (updateResult.isError()) {
            return ApiResult.Error("Unable to update balance")
        }
        //Todo migrate to atomic multi-location update firebase
        val transactionResult = transactionRepository.createTransaction(
            transaction = Transaction(
                id = UUID.randomUUID().toString(),
                amount = total,
                transactionType = TransactionType.MARKETPLACE_PURCHASE,
                description = description
            )
        )
        if (transactionResult.isError()) {
            return ApiResult.Error("Creation of transaction fail")
        }

        return ApiResult.Success(Unit)
    }


    private fun calculateTotalOfProducts(products: List<Product>): Long {
        return products.sumOf { it.price }
    }
}