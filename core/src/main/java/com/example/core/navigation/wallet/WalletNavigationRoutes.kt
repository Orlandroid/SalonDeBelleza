package com.example.core.navigation.wallet

import kotlinx.serialization.Serializable


sealed class WalletNavigationRoutes {
    @Serializable
    data object Balance : WalletNavigationRoutes()

    @Serializable
    data object Transactions : WalletNavigationRoutes()

    @Serializable
    data class TransactionDetail(val transactionId: String) : WalletNavigationRoutes()
}