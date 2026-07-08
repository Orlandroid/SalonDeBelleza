package com.example.data.remote.products.fakestore

import retrofit2.http.GET

interface FakeStoreApi {

    @GET("/products")
    suspend fun getProducts(): List<FakeStoreProduct>
}