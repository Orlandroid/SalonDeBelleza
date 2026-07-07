package com.example.data.api


import com.example.domain.entities.remote.Cart
import com.example.domain.entities.remote.FakeStoreProduct
import retrofit2.http.GET
import retrofit2.http.Path

interface FakeStoreService {

    //FakeStore API
    @GET("/products/category/{categoria}")
    suspend fun getProducts(@Path("categoria") categoria: String): List<FakeStoreProduct>

    @GET("products/categories")
    suspend fun getCategories(): List<String>

    @GET("products/{id}")
    suspend fun getSingleProduct(@Path("id")id: Int): FakeStoreProduct

    @GET("/carts")
    suspend fun getAllCarts():List<Cart>

    @GET("carts/{id}")
    suspend fun getSingleCart(@Path("id") id:Int): Cart
}