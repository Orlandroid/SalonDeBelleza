package com.example.domain.usecases

import com.example.domain.entities.CartInfo
import com.example.domain.repository.BusinessRepository
import com.example.domain.repository.WalletRepository
import com.example.model.state.ApiResult
import com.example.model.state.getContent
import com.example.model.state.getErrorMessage
import com.example.model.state.isError
import javax.inject.Inject

class GetCartInfoUseCase
    @Inject
    constructor(
        private val repository: BusinessRepository,
        private val walletRepository: WalletRepository,
    ) {
        suspend operator fun invoke(): ApiResult<CartInfo> {
            val productsResult = repository.getAllProducts()
            val balanceUserResult = walletRepository.getWallet()
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
                    cartTotal = cartTotal,
                ),
            )
        }
    }
