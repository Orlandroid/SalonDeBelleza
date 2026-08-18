package com.example.domain.wallet

data class Wallet(
    val userId: String,
    val balance: Long,
    val currency: Currency = Currency.USD,
    val createdAt: Long
)