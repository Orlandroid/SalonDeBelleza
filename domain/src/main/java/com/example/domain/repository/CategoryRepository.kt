package com.example.domain.repository

import com.example.domain.CategorySource
import com.example.domain.entities.remote.products.Category
import com.example.domain.entities.remote.products.Product

interface CategoryRepository {

    suspend fun getCategories(
        source: CategorySource
    ): List<Category>

    suspend fun getProductByCategory(
        source: CategorySource,
        category: String
    ): List<Product>
}