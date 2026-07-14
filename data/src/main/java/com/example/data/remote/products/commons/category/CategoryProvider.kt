package com.example.data.remote.products.commons.category

import com.example.domain.entities.remote.products.Category
import com.example.domain.entities.remote.products.Product

interface CategoryProvider {

    suspend fun getCategories(): List<Category>

    suspend fun getProductsByCategory(category: String): List<Product>
}