package com.example.data.remote.products

import com.example.data.remote.products.commons.category.CategoryProvider
import com.example.data.remote.products.commons.product.ProductSource

class CategoryRepositoryImpl(
    private val categoryResolver: CategoryProvider
) : CategoryRepository {

    override suspend fun getCategories(source: ProductSource) = categoryResolver.getCategories()
}