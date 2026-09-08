package com.example.data.di

import com.example.data.TransactionRepositoryImp
import com.example.data.WalletRepositoryImplement
import com.example.di.qualifiers.TransactionReference
import com.example.di.qualifiers.WalletReference
import com.example.domain.repository.WalletRepository
import com.example.domain.transaction.TransactionRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class ModuleWallet {

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

}