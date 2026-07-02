package com.example.domain


import com.example.domain.entities.remote.Cart
import com.example.domain.entities.remote.Product
import com.example.domain.entities.remote.dummyUsers.User
import com.example.domain.perfil.RandomUserResponse
import com.example.domain.state.ApiResult


interface RemoteDataSource {

    suspend fun getProducts(category: String): List<Product>

    suspend fun getSingleCart(id: Int): Cart

    suspend fun randomUser(): RandomUserResponse

    suspend fun getStaffUsers(): ApiResult<List<User>>
}