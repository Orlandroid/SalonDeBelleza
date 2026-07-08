package com.example.data.remote.products.platzy

import retrofit2.http.GET

interface PlatzyApi {

    @GET("api/v1/products")
    suspend fun getProducts(): List<ProductPlatzi>
}