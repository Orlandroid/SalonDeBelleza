package com.example.domain.transaction

import java.util.UUID

data class Transaction(
    val id: String = UUID.randomUUID().toString(),
    val amount: Long = 0L,
    val transactionType: TransactionType = TransactionType.INITIAL_BALANCE,
    val description: String = "",
    val createdAt: Long = 0L
)