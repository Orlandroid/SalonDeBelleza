package com.example.data.remote.products

import com.example.data.remote.products.commons.product.ProductSource
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