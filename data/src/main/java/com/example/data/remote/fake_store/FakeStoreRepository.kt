package com.example.data.remote.fake_store

import com.example.domain.entities.remote.Cart
import com.example.domain.entities.remote.Product

interface FakeStoreRepository {

    suspend fun getProducts(category: String): List<Product>

    suspend fun getSingleProduct(id: Int): Product

    suspend fun getCategories(): List<String>

    suspend fun getSingleCart(id: Int): Cart

}