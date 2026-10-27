package com.example.domain.repository

import com.example.domain.Product
import com.example.domain.entities.products.Category
import com.example.model.state.models.CategorySource

interface CategoryRepository {
    suspend fun getCategories(source: CategorySource): List<Category>

    suspend fun getProductByCategory(
        source: CategorySource,
        category: String,
    ): List<Product>
}
