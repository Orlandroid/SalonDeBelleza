package com.example.domain.entities.remote

data class Cart(
    val id:Int,
    val userId:Int,
    val date:String,
    val products:List<ProductCart>
)

data class ProductCart(
    val productId: Int,
    val quantity:Int
)
