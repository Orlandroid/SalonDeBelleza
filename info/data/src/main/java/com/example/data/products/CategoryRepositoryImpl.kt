package com.example.data.products

import com.example.data.products.commons.category.CategoryProviderResolver
import com.example.domain.CategorySource
import com.example.domain.Product
import com.example.domain.repository.CategoryRepository

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