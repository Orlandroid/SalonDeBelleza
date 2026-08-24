package com.example.domain.transaction

import com.example.domain.state.ApiResult


interface TransactionRepository {

    suspend fun createTransaction(
        transaction: Transaction
    ): ApiResult<Unit>

    suspend fun getTransaction(
        transactionId: String
    ): ApiResult<Transaction>

    suspend fun getTransactions(): ApiResult<List<Transaction>>
}