package com.example.data.remote.transactions

import com.example.di.qualifiers.TransactionReference
import com.example.domain.state.ApiResult
import com.example.domain.transaction.Transaction
import com.example.domain.transaction.TransactionRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import  com.google.firebase.database.ktx.getValue

class TransactionRepositoryImp @Inject constructor(
    @param:TransactionReference private val databaseReference: DatabaseReference,
    private val firebaseAuth: FirebaseAuth
) : TransactionRepository {


    override suspend fun createTransaction(transaction: Transaction): ApiResult<Unit> {
        return runCatching {
            val userId = firebaseAuth.uid
                ?: return ApiResult.Error("User not authenticated")

            databaseReference
                .child(userId)
                .child(transaction.id)
                .setValue(transaction)
                .await()

            ApiResult.Success(Unit)

        }.getOrElse {
            ApiResult.Error(it.message)
        }
    }


    override suspend fun getTransaction(
        transactionId: String
    ): ApiResult<Transaction> {
        return runCatching {
            val userId = firebaseAuth.uid
                ?: return ApiResult.Error("User not authenticated")

            val snapshot = databaseReference.child(userId).child(transactionId).get().await()

            if (!snapshot.exists()) {
                return ApiResult.Error("Transaction not found")
            }

            val transaction = snapshot.getValue<Transaction>()
                ?: return ApiResult.Error("Unable to parse transaction")

            ApiResult.Success(transaction)

        }.getOrElse {
            ApiResult.Error(it.message)
        }
    }

    override suspend fun getTransactions(): ApiResult<List<Transaction>> {
        return runCatching {
            val userId = firebaseAuth.uid
                ?: return ApiResult.Error("User not authenticated")

            val snapshot = databaseReference.child(userId).get().await()

            val transactions = snapshot.children.mapNotNull { it.getValue<Transaction>() }

            ApiResult.Success(transactions)

        }.getOrElse {
            ApiResult.Error(it.message)
        }
    }


}