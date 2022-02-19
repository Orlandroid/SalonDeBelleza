package com.example.citassalon.data.retrofit

import com.example.citassalon.data.models.Products
import retrofit2.http.GET

interface FakeStoreService {

    @GET("products/")
    suspend fun getProducts(): List<Products>
}