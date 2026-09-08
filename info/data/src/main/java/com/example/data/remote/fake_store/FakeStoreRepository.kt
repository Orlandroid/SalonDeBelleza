package com.example.data.remote.fake_store

import com.example.data.products.fakestore.FakeStoreProduct
import com.example.domain.entities.Cart

interface FakeStoreRepository {

    suspend fun getProducts(category: String): List<FakeStoreProduct>

    suspend fun getSingleProduct(id: Int): FakeStoreProduct

    suspend fun getCategories(): List<String>

    suspend fun getSingleCart(id: Int): Cart

}