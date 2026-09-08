package com.example.data.api


import com.example.data.products.dummyjson.ProductsResponse
import com.example.domain.Product
import com.example.domain.entities.categories.Category
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