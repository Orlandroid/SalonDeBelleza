package com.example.data.di

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class WalletReference

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class TransactionReference