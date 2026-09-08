package com.example.domain.entities

import com.example.domain.Product


data class CartInfo(
    val userMoney: Long,
    val products: List<Product>,
    val cartTotal: Long
)