package com.example.di.qualifiers

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AppointmentsRef

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class UsersRef

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ImagesRef

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class WalletReference

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class TransactionReference

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class LoyaltyRef

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class LoyaltyTransactionsRef

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class RewardsRef

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class PromotionCodesRef
