package com.example.data.remote.products

import com.example.data.remote.products.commons.category.CategorySource
import com.example.domain.entities.remote.products.Category

interface CategoryRepository {

    suspend fun getCategories(
        source: CategorySource
    ): List<Category>
}