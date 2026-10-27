package com.example.domain.repository

import com.example.domain.Product
import com.example.model.state.models.ProductSource

interface ProductsRepository {
    suspend fun getProducts(source: ProductSource): List<Product>

    suspend fun getSingleProduct(
        source: ProductSource,
        id: Int,
    ): Product
}
