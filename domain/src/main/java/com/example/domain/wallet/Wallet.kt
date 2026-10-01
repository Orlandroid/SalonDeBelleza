package com.example.domain.wallet

import com.example.model.state.models.Currency

data class Wallet(
    val userId: String = "",
    val balance: Long = 0L,
    val currency: Currency = Currency.USD,
    val createdAt: Long = 0L
)