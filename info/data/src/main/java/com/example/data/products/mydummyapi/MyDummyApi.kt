package com.example.data.products.mydummyapi

import retrofit2.http.GET

interface MyDummyApi {

    @GET("products/")
    suspend fun getProducts(): List<ProductMyDummyApi>

}