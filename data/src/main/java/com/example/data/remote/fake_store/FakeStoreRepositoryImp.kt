package com.example.data.remote.fake_store

import com.example.data.api.FakeStoreService
import com.example.domain.entities.remote.Cart
import com.example.domain.entities.remote.Product

class FakeStoreRepositoryImp(private val api: FakeStoreService): FakeStoreRepository {

    override suspend fun getProducts(category: String): List<Product> {
        return api.getProducts(categoria = category)
    }

    override suspend fun getSingleProduct(id: Int): Product {
        return api.getSingleProduct(id = id)
    }

    override suspend fun getCategories(): List<String> {
        return api.getCategories()
    }

    override suspend fun getSingleCart(id: Int): Cart {
        return api.getSingleCart(id = id)
    }
}