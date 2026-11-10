package com.example.data.di

import com.example.data.TransactionRepositoryImp
import com.example.data.WalletRepositoryImplement
import com.example.domain.repository.WalletRepository
import com.example.domain.transaction.TransactionRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ModuleWallet {

    private const val WALLET = "wallets"
    private const val TRANSACTIONS = "transactions"


    @Singleton
    @Provides
    fun provideWalletRepository(
        @WalletReference databaseReference: DatabaseReference,
        firebaseAuth: FirebaseAuth
    ): WalletRepository =
        WalletRepositoryImplement(
            databaseReference = databaseReference,
            firebaseAuth = firebaseAuth
        )

    @Singleton
    @Provides
    fun provideTransactionRepository(
        @TransactionReference databaseReference: DatabaseReference,
        firebaseAuth: FirebaseAuth
    ): TransactionRepository =
        TransactionRepositoryImp(
            databaseReference = databaseReference,
            firebaseAuth = firebaseAuth
        )

    @Singleton
    @Provides
    @WalletReference
    fun provideFirebaseRealtimeWalletReference(firebaseDatabase: FirebaseDatabase): DatabaseReference =
        firebaseDatabase.reference.child(WALLET)

    @Singleton
    @Provides
    @TransactionReference
    fun provideFirebaseRealtimeTransactionReference(firebaseDatabase: FirebaseDatabase): DatabaseReference =
        firebaseDatabase.reference.child(TRANSACTIONS)
}
