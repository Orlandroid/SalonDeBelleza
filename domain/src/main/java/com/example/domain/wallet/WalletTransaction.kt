package com.example.domain.wallet

data class WalletTransaction(
    val id: String,
    val userId: String,
    val amount: Long,
    val type: TransactionType,
    val description: String,
    val createdAt: Long
)