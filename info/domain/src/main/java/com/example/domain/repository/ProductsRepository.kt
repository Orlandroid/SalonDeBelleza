package com.example.domain.repository

import com.example.domain.Product
import com.example.domain.ProductSource

interface ProductRepository {

    suspend fun getProducts(
        source: ProductSource
    ): List<Product>

    suspend fun getSingleProduct(
        source: ProductSource,
        id: Int
    ): Product
}