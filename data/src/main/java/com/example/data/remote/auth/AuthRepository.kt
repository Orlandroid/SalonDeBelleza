package com.example.data.remote.auth

import com.example.domain.entities.remote.products.Product
import com.example.domain.entities.remote.products.ProductResponse


interface AuthRepository {

    suspend fun getProducts(): ProductResponse

    suspend fun getSingleProduct(productId:Int): Product
}