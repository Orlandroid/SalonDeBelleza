package com.example.domain.wallet

data class Balance(
    val userName: String,
    val balance: Long,
    val currency: Currency,
    val createdAtMillis: Long,
    val userId: String
)
