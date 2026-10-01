package com.example.data.products.platzy

import com.example.data.products.commons.category.CategoryProvider
import com.example.data.products.commons.product.ProductProvider
import com.example.domain.Product
import com.example.domain.entities.products.Category
import javax.inject.Inject

class PlatzyProductProvider
    @Inject
    constructor(
        private val api: PlatzyApi,
    ) : ProductProvider,
        CategoryProvider {
        override suspend fun getProducts(): List<Product> = api.getProducts().map { it.toDomain() }

        override suspend fun getSingleProduct(id: Int): Product = api.getSingleProduct(id).toDomain()

        override suspend fun getCategories(): List<Category> =
            api.getCategories().map {
                Category(
                    id = it.id.toString(),
                    name = it.name,
                    image = it.image,
                    slug = it.slug,
                )
            }

        override suspend fun getProductsByCategory(category: String): List<Product> = emptyList<Product>()
    }
