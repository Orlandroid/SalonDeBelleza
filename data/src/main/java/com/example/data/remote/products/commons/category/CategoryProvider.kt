package com.example.data.remote.products.commons.category

import com.example.domain.entities.remote.products.Category

interface CategoryProvider {

    suspend fun getCategories(): List<Category>
}