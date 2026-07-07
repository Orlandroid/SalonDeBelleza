package com.example.data.api

import com.example.data.remote.products.mydummyapi.ProductMyDummyApi
import com.example.data.remote.products.dummyjson.ProductsResponse
import com.example.data.remote.products.platzy.ProductPlatzi
import retrofit2.http.Url

interface Products {


    suspend fun getProductsDummyJson(@Url url: String): ProductsResponse

    suspend fun getProductsMyDummyApi(@Url url: String): List<ProductMyDummyApi>

    suspend fun getProductsPlazy(@Url url: String): List<ProductPlatzi>

}