package com.example.domain.wallet

import com.example.domain.state.ApiResult
import kotlinx.coroutines.flow.Flow

interface WalletRepository {

    suspend fun getWallet(): ApiResult<Wallet>

    suspend fun createWallet(wallet: Wallet): ApiResult<Unit>

    fun observeWallet(userId: String): Flow<Wallet?>

    fun observeTransactions(
        userId: String
    ): Flow<List<WalletTransaction>>

    suspend fun spendMoney(
        userId: String,
        amount: Long,
        type: TransactionType,
        description: String
    ): ApiResult<Unit>

    suspend fun addMoney(
        userId: String,
        amount: Long,
        type: TransactionType,
        description: String
    ): ApiResult<Unit>
}