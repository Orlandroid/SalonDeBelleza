package com.example.data.remote.products

import com.example.data.remote.products.commons.ProductSource
import com.example.domain.entities.remote.products.Product

interface ProductRepository {

    suspend fun getProducts(
        source: ProductSource
    ): List<Product>
}