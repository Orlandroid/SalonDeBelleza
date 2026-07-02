package com.example.data.remote.products

import com.example.domain.entities.remote.Product

interface ProductsRepository {

    suspend fun getProducts(
        category: String
    ): List<Product>
}