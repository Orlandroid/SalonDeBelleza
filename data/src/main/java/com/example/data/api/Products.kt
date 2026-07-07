package com.example.data.api

import com.example.domain.entities.remote.products.ProductMyDummyApi
import com.example.domain.entities.remote.products.ProductsResponse
import com.example.domain.entities.remote.products.ProductPlatzi
import retrofit2.http.Url

interface Products {


    suspend fun getProductsDummyJson(@Url url: String): ProductsResponse

    suspend fun getProductsMyDummyApi(@Url url: String): List<ProductMyDummyApi>

    suspend fun getProductsPlazy(@Url url: String): List<ProductPlatzi>

}