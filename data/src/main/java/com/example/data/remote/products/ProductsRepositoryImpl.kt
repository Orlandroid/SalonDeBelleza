package com.example.data.remote.products

import com.example.data.remote.products.commons.ProductProviderResolver
import com.example.data.remote.products.commons.ProductSource
import com.example.domain.entities.remote.products.Product

class ProductRepositoryImpl(
    private val resolver: ProductProviderResolver
) : ProductRepository {

    override suspend fun getProducts(
        source: ProductSource
    ): List<Product> {
        return resolver
            .resolve(source)
            .getProducts()
    }
}