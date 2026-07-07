package com.example.data.remote.fake_store

import com.example.domain.entities.remote.Cart
import com.example.domain.entities.remote.FakeStoreProduct

interface FakeStoreRepository {

    suspend fun getProducts(category: String): List<FakeStoreProduct>

    suspend fun getSingleProduct(id: Int): FakeStoreProduct

    suspend fun getCategories(): List<String>

    suspend fun getSingleCart(id: Int): Cart

}