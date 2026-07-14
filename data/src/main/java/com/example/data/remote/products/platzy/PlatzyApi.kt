package com.example.data.remote.products.platzy

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PlatzyApi {

    @GET("api/v1/products")
    suspend fun getProducts(): List<ProductPlatzi>

    @GET("api/v1/products/{id}")
    suspend fun getSingleProduct(@Path("id") id: Int): ProductPlatzi

    @GET("api/v1/categories")
    suspend fun getCategories(): List<PlaztlyCategory>

    @GET("api/v1/products")
    suspend fun getProductsByCategory(@Query("categoryId") categoryId: String): List<ProductPlatzi>
}