package com.example.data.remote.loyalty

import com.example.di.qualifiers.LoyaltyRef
import com.example.domain.loyalty.Loyalty
import com.example.domain.repository.LoyaltyRepository
import com.example.domain.state.ApiResult
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.getValue
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class LoyaltyRepositoryImpl @Inject constructor(
    @LoyaltyRef private val loyaltyRef: DatabaseReference
) : LoyaltyRepository {

    override suspend fun initializeLoyalty(userId: String): ApiResult<Unit> {
        return runCatching {
            val initialLoyalty = Loyalty(userId = userId)
            loyaltyRef.child(userId).setValue(initialLoyalty).await()
            ApiResult.Success(Unit)
        }.getOrElse {
            ApiResult.Error(it.message)
        }
    }

    override suspend fun getLoyalty(userId: String): ApiResult<Loyalty> {
        return runCatching {
            val snapshot = loyaltyRef.child(userId).get().await()
            val loyalty = snapshot.getValue<Loyalty>() ?: Loyalty(userId = userId)
            ApiResult.Success(loyalty)
        }.getOrElse {
            ApiResult.Error(it.message)
        }
    }

    override suspend fun updateLoyalty(loyalty: Loyalty): ApiResult<Unit> {
        return runCatching {
            loyaltyRef.child(loyalty.userId).setValue(loyalty).await()
            ApiResult.Success(Unit)
        }.getOrElse {
            ApiResult.Error(it.message)
        }
    }
}
