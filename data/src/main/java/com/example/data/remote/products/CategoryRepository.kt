package com.example.data.remote.products

import com.example.data.remote.products.commons.product.ProductSource
import com.example.domain.entities.remote.products.Category

interface CategoryRepository {

    suspend fun getCategories(
        source: ProductSource
    ): List<Category>
}