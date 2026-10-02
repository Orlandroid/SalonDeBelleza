package com.example.domain.usecases

import com.example.domain.UserProfile
import com.example.domain.UserSessionStatus
import com.example.domain.repository.LoyaltyRepository
import com.example.domain.repository.UserRepository
import com.example.domain.repository.WalletRepository
import com.example.model.state.ApiResult
import com.example.model.state.getContent
import com.example.model.state.getErrorMessage
import com.example.model.state.getResultOrNull
import com.example.model.state.isSuccess
import com.example.model.state.models.UserRole
import javax.inject.Inject

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
        val getUserResult = userRepository.getUser()
        var name = ""
        var phone = ""
        var role = UserRole.CUSTOMER
        var userSession = UserSessionStatus.INACTIVE
        if (getUserResult.isSuccess()) {
            val userDetail = getUserResult.getContent()
            name = userDetail.name
            phone = userDetail.phone
            userSession = UserSessionStatus.ACTIVE
            role = try {
                userDetail.role
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
            email = user.email,
            uid = user.uid,
            phone = phone,
            money = money,
            image = image,
            sessionStatus = userSession,
            loyalty = loyalty,
            coupons = cupons,
            role = role
        )
        return ApiResult.Success(userInfo)
    }
}
