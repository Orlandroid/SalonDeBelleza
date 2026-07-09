package com.example.data.remote.products.fakestore

import retrofit2.http.GET
import retrofit2.http.Path

interface FakeStoreApi {

    @GET("/products")
    suspend fun getProducts(): List<FakeStoreProduct>

    @GET("products/{id}")
    suspend fun getSingleProduct(@Path("id")id: Int): FakeStoreProduct
}