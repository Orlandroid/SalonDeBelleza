package com.example.data.api


import com.example.domain.entities.remote.products.Product
import com.example.domain.entities.remote.products.ProductResponse
import retrofit2.http.GET
import retrofit2.http.Path


interface DummyJsonApi {

    @GET("products")
    suspend fun getProducts(): ProductResponse


    @GET("products/categories")
    suspend fun getCategories(): List<String>

    @GET("products/{productId}")
    suspend fun getSingleProduct(@Path("productId") product: Int): Product
}