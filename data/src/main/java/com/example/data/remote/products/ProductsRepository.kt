package com.example.data.remote.products

import com.example.domain.entities.remote.FakeStoreProduct

interface ProductsRepository {

    suspend fun getProducts(
        category: String
    ): List<FakeStoreProduct>
}