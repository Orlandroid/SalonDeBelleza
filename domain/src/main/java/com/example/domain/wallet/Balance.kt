package com.example.domain.wallet

import com.example.model.state.models.Currency

data class Balance(
    val userName: String,
    val balance: Long,
    val currency: Currency,
    val createdAtMillis: Long,
    val userId: String
)