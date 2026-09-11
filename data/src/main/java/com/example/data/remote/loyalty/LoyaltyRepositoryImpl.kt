package com.example.data.remote.loyalty

import com.example.di.qualifiers.LoyaltyRef
import com.example.di.qualifiers.LoyaltyTransactionsRef
import com.example.di.qualifiers.PromotionCodesRef
import com.example.di.qualifiers.RewardsRef
import com.example.domain.loyalty.Loyalty
import com.example.domain.loyalty.LoyaltyTransaction
import com.example.domain.loyalty.PromotionCode
import com.example.domain.loyalty.Reward
import com.example.domain.repository.LoyaltyRepository
import com.example.domain.state.ApiResult
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.getValue
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class LoyaltyRepositoryImpl @Inject constructor(
    @LoyaltyRef private val loyaltyRef: DatabaseReference,
    @LoyaltyTransactionsRef private val loyaltyTransactionsRef: DatabaseReference,
    @RewardsRef private val rewardsRef: DatabaseReference,
    @PromotionCodesRef private val promotionCodesRef: DatabaseReference
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

    override suspend fun addLoyaltyTransaction(
        userId: String,
        transaction: LoyaltyTransaction
    ): ApiResult<Unit> {
        return runCatching {
            loyaltyTransactionsRef.child(userId).child(transaction.id).setValue(transaction).await()
            ApiResult.Success(Unit)
        }.getOrElse {
            ApiResult.Error(it.message)
        }
    }

    override suspend fun getRewards(): ApiResult<List<Reward>> {
        return runCatching {
            val snapshot = rewardsRef.get().await()
            val rewards = snapshot.children.mapNotNull { it.getValue<Reward>() }
            ApiResult.Success(rewards)
        }.getOrElse {
            ApiResult.Error(it.message)
        }
    }

    override suspend fun addPromotionCode(
        userId: String,
        promotionCode: PromotionCode
    ): ApiResult<Unit> {
        return runCatching {
            promotionCodesRef.child(userId).child(promotionCode.id).setValue(promotionCode).await()
            ApiResult.Success(Unit)
        }.getOrElse {
            ApiResult.Error(it.message)
        }
    }

    override suspend fun getPromotionCodes(userId: String): ApiResult<List<PromotionCode>> {
        return runCatching {
            val snapshot = promotionCodesRef.child(userId).get().await()
            val codes = snapshot.children.mapNotNull { it.getValue<PromotionCode>() }
                .filter { !it.used }
            ApiResult.Success(codes)
        }.getOrElse {
            ApiResult.Error(it.message)
        }
    }
}
