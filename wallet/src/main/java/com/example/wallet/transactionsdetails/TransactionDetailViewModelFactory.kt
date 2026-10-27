package com.example.wallet.transactionsdetails

import dagger.assisted.AssistedFactory

@AssistedFactory
interface TransactionDetailViewModelFactory {
    fun create(transactionId: String): TransactionDetailViewmodel
}
