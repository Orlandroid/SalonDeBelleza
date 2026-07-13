package com.example.data.remote.products

import com.example.data.remote.products.commons.category.CategoryProviderResolver
import com.example.data.remote.products.commons.category.CategorySource

class CategoryRepositoryImpl(
    private val categoryResolver: CategoryProviderResolver
) : CategoryRepository {

    override suspend fun getCategories(source: CategorySource) =
        categoryResolver.resolve(source = source).getCategories()
}