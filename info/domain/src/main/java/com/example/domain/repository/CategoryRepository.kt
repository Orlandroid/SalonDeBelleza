package com.example.domain.repository

import com.example.domain.CategorySource
import com.example.domain.Product
import com.example.domain.entities.products.Category

interface CategoryRepository {

    suspend fun getCategories(
        source: CategorySource
    ): List<Category>

    suspend fun getProductByCategory(
        source: CategorySource,
        category: String
    ): List<Product>
}