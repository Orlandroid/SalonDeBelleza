package com.example.domain.usecases.loyalty

import com.example.domain.loyalty.PromotionCode
import com.example.domain.repository.LoyaltyRepository
import com.example.model.state.ApiResult
import com.example.model.state.getContent
import com.example.model.state.isError
import javax.inject.Inject

class VerifyPromoCodeUseCase @Inject constructor(
    private val repository: LoyaltyRepository
) {
    suspend operator fun invoke(userId: String, codeStr: String): ApiResult<PromotionCode> {

        val result = repository.getPromotionCodes(userId)
        if (result.isError()) {
            return ApiResult.Error("Verification error.")
        }

        val codes = result.getContent()
        val validCode = codes.find { it.code.equals(codeStr, ignoreCase = true) && !it.used }

        return if (validCode != null) {
            ApiResult.Success(validCode)
        } else {
            ApiResult.Error("Invalid or already used code")
        }
    }
}