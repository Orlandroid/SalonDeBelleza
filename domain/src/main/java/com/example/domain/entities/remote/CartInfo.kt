package com.example.domain.entities.remote

import com.example.domain.entities.remote.products.Product

data class CartInfo(
    val userMoney: Long,
    val products: List<Product>,
    val cartTotal: Long
)