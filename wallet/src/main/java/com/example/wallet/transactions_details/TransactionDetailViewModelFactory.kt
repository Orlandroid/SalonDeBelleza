package com.example.wallet.transactions_details

import dagger.assisted.AssistedFactory


@AssistedFactory
interface TransactionDetailViewModelFactory {
    fun create(transactionId: String): TransactionDetailViewmodel
}