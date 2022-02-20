package com.example.citassalon.data.retrofit

import com.example.citassalon.data.models.Products
import retrofit2.http.GET
import retrofit2.http.Path

interface FakeStoreService {

    @GET("/products/category/{categoria}")
    suspend fun getProducts(@Path("categoria") categoria: String): List<Products>

    @GET("products/categories/")
    suspend fun getCategories(): List<String>
}