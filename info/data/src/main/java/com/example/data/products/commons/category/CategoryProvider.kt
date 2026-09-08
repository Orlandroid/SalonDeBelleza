package com.example.data.products.commons.category

import com.example.domain.Product
import com.example.domain.entities.products.Category

interface CategoryProvider {

    suspend fun getCategories(): List<Category>

    suspend fun getProductsByCategory(category: String): List<Product>
}