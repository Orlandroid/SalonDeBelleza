package com.example.data.remote.products

import com.example.data.remote.products.commons.product.ProductProviderResolver
import com.example.domain.ProductSource
import com.example.domain.entities.remote.products.Product
import com.example.domain.repository.ProductRepository

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

    override suspend fun getSingleProduct(source: ProductSource, id: Int) = resolver.resolve(source).getSingleProduct(id)
}