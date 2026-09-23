package com.example.domain.use_cases

import com.example.domain.UserSessionStatus
import com.example.domain.entities.UserProfile
import com.example.domain.entities.UserRole
import com.example.domain.repository.LoyaltyRepository
import com.example.domain.repository.UserRepository
import com.example.domain.repository.WalletRepository
import com.example.domain.state.ApiResult
import com.example.domain.state.getContent
import com.example.domain.state.getErrorMessage
import com.example.domain.state.getResultOrNull
import com.example.domain.state.isError
import com.example.domain.state.isSuccess
import javax.inject.Inject
import kotlin.collections.emptyList

class GetUserInfoUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val walletRepository: WalletRepository,
    private val loyaltyRepository: LoyaltyRepository
) {


    suspend operator fun invoke(): ApiResult<UserProfile> {
        val userResult = userRepository.getUser()
        if (userResult is ApiResult.Error) {
            return ApiResult.Error(userResult.getErrorMessage())
        }
        val user = userResult.getResultOrNull() ?: return ApiResult.Error("User not found")
        val moneyResult = walletRepository.getWallet()
        val money = if (moneyResult.isSuccess()) {
            moneyResult.getContent().balance
        } else {
            0L
        }
        var image: String? = null
        val imageResult = userRepository.getUserImage()
        if (imageResult.isSuccess()) {
            image = imageResult.getContent()
        }
        val nameAndPhoneResult = userRepository.getNameAndPhone()
        var name = ""
        var phone = ""
        var role = UserRole.CUSTOMER
        if (nameAndPhoneResult.isSuccess()) {
            val userDetail = nameAndPhoneResult.getContent()
            name = userDetail.name
            phone = userDetail.phone
            role = try {
                UserRole.valueOf(userDetail.role.uppercase())
            } catch (e: Exception) {
                UserRole.CUSTOMER
            }
        }
        val loyaltyResult = loyaltyRepository.getLoyalty(user.uid)
        val loyalty = if (loyaltyResult.isSuccess()) {
            loyaltyResult.getContent()
        } else {
            null
        }
        val cuponsResult = loyaltyRepository.getPromotionCodes(user.uid)
        val cupons = if (cuponsResult.isSuccess()) {
            cuponsResult.getContent()
        } else {
            emptyList()
        }
        val userInfo = UserProfile(
            name = name,
            email = user.email.orEmpty(),
            uid = user.uid,
            phone = phone,
            money = money,
            image = image,
            sessionStatus = getUserSessionStatus(),
            loyalty = loyalty,
            coupons = cupons,
            role = role
        )
        return ApiResult.Success(userInfo)
    }


    private fun getUserSessionStatus(): UserSessionStatus {
        val userResult = userRepository.getUser()

        if (userResult.isError()) {
            return UserSessionStatus.INACTIVE
        }

        if (userResult.getResultOrNull() == null) {
            return UserSessionStatus.INACTIVE
        }

        return UserSessionStatus.ACTIVE

    }
}
