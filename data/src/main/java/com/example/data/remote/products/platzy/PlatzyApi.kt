package com.example.data.remote.products.platzy

import retrofit2.http.GET
import retrofit2.http.Path

interface PlatzyApi {

    @GET("api/v1/products")
    suspend fun getProducts(): List<ProductPlatzi>

    @GET("products/{id}")
    suspend fun getSingleProduct(@Path("id")id: Int): ProductPlatzi
}