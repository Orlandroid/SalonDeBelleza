package com.example.domain.wallet

import com.example.domain.state.ApiResult
import com.example.domain.transaction.TransactionType
import kotlinx.coroutines.flow.Flow

interface WalletRepository {

    suspend fun getWallet(): ApiResult<Wallet>

    suspend fun createWallet(wallet: Wallet): ApiResult<Unit>

    fun observeWallet(userId: String): Flow<Wallet?>


    suspend fun updateBalance(
        newBalance: Long
    ): ApiResult<Unit>

    suspend fun addMoney(
        userId: String,
        amount: Long,
        type: TransactionType,
        description: String
    ): ApiResult<Unit>
}