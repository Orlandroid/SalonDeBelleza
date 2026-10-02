package com.example.data.products

import com.example.data.products.commons.product.ProductProviderResolver
import com.example.domain.Product
import com.example.domain.repository.ProductsRepository
import com.example.model.state.models.ProductSource

class ProductsRepositoryImpl(
    private val resolver: ProductProviderResolver,
) : ProductsRepository {
    override suspend fun getProducts(source: ProductSource): List<Product> =
        resolver
            .resolve(source)
            .getProducts()

    override suspend fun getSingleProduct(
        source: ProductSource,
        id: Int,
    ) = resolver.resolve(source).getSingleProduct(id)
}
