package com.example.domain.wallet

data class Wallet(
    val userId: String = "",
    val balance: Long = 0L,
    val currency: Currency = Currency.USD,
    val createdAt: Long = 0L
)