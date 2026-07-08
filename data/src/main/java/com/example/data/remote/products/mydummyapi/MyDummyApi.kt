package com.example.data.remote.products.mydummyapi

import com.example.data.remote.products.fakestore.FakeStoreProduct
import retrofit2.http.GET

interface MyDummyApi {

    @GET("/products/")
    suspend fun getProducts(): List<FakeStoreProduct>

}