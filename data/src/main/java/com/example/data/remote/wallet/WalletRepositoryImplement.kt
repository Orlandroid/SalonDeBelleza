package com.example.data.remote.wallet

import com.example.di.qualifiers.WalletReference
import com.example.domain.state.ApiResult
import com.example.domain.wallet.TransactionType
import com.example.domain.wallet.Wallet
import com.example.domain.wallet.WalletRepository
import com.example.domain.wallet.WalletTransaction
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import kotlin.coroutines.resume

class WalletRepositoryImplement @Inject constructor(
    @param:WalletReference private val databaseReference: DatabaseReference,
    private val firebaseAuth: FirebaseAuth
) :
    WalletRepository {
    override suspend fun getWallet(): ApiResult<Wallet> =
        suspendCancellableCoroutine { continuation ->
            val userId = firebaseAuth.uid
            if (userId == null) {
                continuation.resume(ApiResult.Error("Error"))
                return@suspendCancellableCoroutine
            }
            databaseReference
                .child(userId)
                .addListenerForSingleValueEvent(
                    object : ValueEventListener {

                        override fun onDataChange(snapshot: DataSnapshot) {
                            if (!snapshot.exists()) {
                                continuation.resume(
                                    ApiResult.Error("Wallet not found")
                                )
                                return
                            }

                            val wallet = snapshot.getValue<Wallet>()

                            if (wallet == null) {
                                continuation.resume(
                                    ApiResult.Error("Unable to parse wallet")
                                )
                                return
                            }

                            continuation.resume(
                                ApiResult.Success(wallet)
                            )
                        }

                        override fun onCancelled(error: DatabaseError) {
                            continuation.resume(
                                ApiResult.Error(error.message)
                            )
                        }
                    }
                )
        }


    override suspend fun createWallet(wallet: Wallet): ApiResult<Unit> {
        runCatching {
            databaseReference.child(wallet.userId).setValue(wallet).await()
            return ApiResult.Success(Unit)
        }.getOrElse {
            return ApiResult.Error(it.message)
        }
    }

    override fun observeWallet(userId: String): Flow<Wallet?> {
        return emptyFlow()
    }

    override fun observeTransactions(userId: String): Flow<List<WalletTransaction>> {
        return emptyFlow()
    }

    override suspend fun spendMoney(
        userId: String,
        amount: Long,
        type: TransactionType,
        description: String
    ): ApiResult<Unit> {
        return ApiResult.Error()
    }

    override suspend fun addMoney(
        userId: String,
        amount: Long,
        type: TransactionType,
        description: String
    ): ApiResult<Unit> {
        return ApiResult.Error()
    }


}