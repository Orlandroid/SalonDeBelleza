package com.example.data.remote.products

import com.example.data.remote.products.fakestore.FakeStoreProduct

interface ProductsRepository {

    suspend fun getProducts(
        category: String
    ): List<FakeStoreProduct>
}