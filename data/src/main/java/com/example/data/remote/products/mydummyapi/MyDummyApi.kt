package com.example.data.remote.products.mydummyapi

import retrofit2.http.GET

interface MyDummyApi {

    @GET("products/")
    suspend fun getProducts(): List<ProductMyDummyApi>

}