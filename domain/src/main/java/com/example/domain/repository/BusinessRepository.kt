package com.example.domain.repository

import com.example.domain.entities.remote.dummyUsers.User
import com.example.domain.entities.remote.products.Product
import com.example.domain.state.ApiResult


interface BusinessRepository {
    suspend fun getAllProducts(): ApiResult<List<Product>>
    suspend fun deleteAllProducts(): ApiResult<Unit>

    suspend fun getStaffUsers(): ApiResult<List<User>>

    suspend fun addProduct(product: Product): ApiResult<Unit>
}