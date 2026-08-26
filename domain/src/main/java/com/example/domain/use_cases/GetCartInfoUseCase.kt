package com.example.domain.use_cases

import com.example.domain.entities.remote.CartInfo
import com.example.domain.repository.BusinessRepository
import com.example.domain.state.ApiResult
import com.example.domain.state.getContent
import com.example.domain.state.getErrorMessage
import com.example.domain.state.isError
import javax.inject.Inject


class GetCartInfoUseCase @Inject constructor(
    private val repository: BusinessRepository,
    private val getWalletUseCase: GetWalletUseCase
) {

    suspend operator fun invoke(): ApiResult<CartInfo> {

        val productsResult = repository.getAllProducts()
        val balanceUserResult = getWalletUseCase.invoke()
        if (productsResult.isError()) {
            return ApiResult.Error(productsResult.getErrorMessage())
        }
        if (balanceUserResult.isError()) {
            return ApiResult.Error(balanceUserResult.getErrorMessage())
        }

        var cartTotal = 0L

        productsResult.getContent().forEach {
            cartTotal += it.price * it.quantity
        }

        return ApiResult.Success(
            CartInfo(
                products = productsResult.getContent(),
                userMoney = balanceUserResult.getContent().balance,
                cartTotal = cartTotal
            )
        )
    }
}