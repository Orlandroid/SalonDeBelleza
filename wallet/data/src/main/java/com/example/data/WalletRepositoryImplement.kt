package com.example.data

import com.example.di.qualifiers.WalletReference
import com.example.domain.repository.WalletRepository
import com.example.domain.wallet.Wallet
import com.example.model.state.ApiResult
import com.example.model.state.models.TransactionType
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.getValue
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class WalletRepositoryImplement
    @Inject
    constructor(
        @param:WalletReference private val databaseReference: DatabaseReference,
        private val firebaseAuth: FirebaseAuth
    ) : WalletRepository {
        override suspend fun getWallet(): ApiResult<Wallet> {
            return runCatching {
                val userId =
                    firebaseAuth.uid
                        ?: return ApiResult.Error("User is not authenticated")

                val snapshot = databaseReference.child(userId).get().await()

                if (!snapshot.exists()) {
                    return ApiResult.Error("Wallet not found")
                }

                val wallet =
                    snapshot.getValue<Wallet>()
                        ?: return ApiResult.Error("Unable to parse wallet")

                ApiResult.Success(wallet)
            }.getOrElse {
                ApiResult.Error(it.message.orEmpty())
            }
        }

        override suspend fun createWallet(wallet: Wallet): ApiResult<Unit> =
            runCatching {
                databaseReference.child(wallet.userId).setValue(wallet).await()
                ApiResult.Success(Unit)
            }.getOrElse {
                ApiResult.Error(it.message.orEmpty())
            }

        override suspend fun updateBalance(newBalance: Long): ApiResult<Unit> {
            return runCatching {
                val userId =
                    firebaseAuth.uid
                        ?: return ApiResult.Error("User is not authenticated")
                databaseReference
                    .child(userId)
                    .child("balance")
                    .setValue(newBalance)
                    .await()
                ApiResult.Success(Unit)
            }.getOrElse {
                ApiResult.Error(it.message.orEmpty())
            }
        }

        override suspend fun addMoney(
            userId: String,
            amount: Long,
            type: TransactionType,
            description: String
        ): ApiResult<Unit> = ApiResult.Error("Not implemented")

        override fun observeWallet(userId: String): Flow<Wallet?> = emptyFlow()
    }
