package com.example.data.products.platzy

import com.example.data.products.commons.category.CategoryProvider
import com.example.domain.Product
import com.example.domain.entities.products.Category
import javax.inject.Inject


class PlatzyCategoryProvider @Inject constructor(
    private val api: PlatzyApi
) : CategoryProvider {
    override suspend fun getCategories(): List<Category> {
        return api.getCategories().map { Category(id = it.id.toString(), name = it.name) }
    }

    override suspend fun getProductsByCategory(category: String): List<Product> =
        api.getProductsByCategory(category).map { it.toDomain() }

}