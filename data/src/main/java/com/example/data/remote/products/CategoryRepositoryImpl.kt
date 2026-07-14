package com.example.data.remote.products

import com.example.data.remote.products.commons.category.CategoryProviderResolver
import com.example.data.remote.products.commons.category.CategorySource
import com.example.domain.entities.remote.products.Product

class CategoryRepositoryImpl(
    private val categoryResolver: CategoryProviderResolver
) : CategoryRepository {

    override suspend fun getCategories(source: CategorySource) =
        categoryResolver.resolve(source = source).getCategories()

    override suspend fun getProductByCategory(
        source: CategorySource,
        category: String
    ): List<Product> {
        return categoryResolver.resolve(source).getProductsByCategory(category = category)
    }
}