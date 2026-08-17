package com.example.domain.repository

import com.example.domain.ProductSource
import com.example.domain.entities.remote.products.Product

interface ProductRepository {

    suspend fun getProducts(
        source: ProductSource
    ): List<Product>

    suspend fun getSingleProduct(
        source: ProductSource,
        id: Int
    ): Product
}