package com.example.data.api


import com.example.domain.entities.remote.categories.Category
import com.example.domain.entities.remote.products.Product
import com.example.domain.entities.remote.products.ProductsResponse
import retrofit2.http.GET
import retrofit2.http.Path


interface DummyJsonApi {

    @GET("products")
    suspend fun getProducts(): ProductsResponse


    @GET("products/categories")
    suspend fun getCategories(): List<Category>

    @GET("products/{productId}")
    suspend fun getSingleProduct(@Path("productId") product: Int): Product
}