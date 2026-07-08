package com.example.data.remote.products.dummyjson

import retrofit2.http.GET

interface DummyJsonApiV2 {

    @GET("products")
    suspend fun getProducts(): ProductsResponse
}