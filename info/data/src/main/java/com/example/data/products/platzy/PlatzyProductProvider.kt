package com.example.data.products.platzy

import com.example.data.products.commons.category.CategoryProvider
import com.example.data.products.commons.product.ProductProvider
import com.example.domain.Product
import javax.inject.Inject
import com.example.domain.entities.products.Category

class PlatzyProductProvider @Inject constructor(
    private val api: PlatzyApi
) : ProductProvider, CategoryProvider {

    override suspend fun getProducts(): List<Product> {
        return api.getProducts().map { it.toDomain() }
    }

    override suspend fun getSingleProduct(id: Int): Product {
        return api.getSingleProduct(id).toDomain()
    }

    override suspend fun getCategories(): List<Category> {
        return api.getCategories().map {
            Category(
                id = it.id.toString(),
                name = it.name,
                image = it.image,
                slug = it.slug
            )
        }
    }

    override suspend fun getProductsByCategory(category: String): List<Product> {
        return emptyList<Product>()
    }
}