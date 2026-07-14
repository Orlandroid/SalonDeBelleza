package com.example.data.remote.products.platzy

import com.example.data.remote.products.commons.category.CategoryProvider
import com.example.domain.entities.remote.products.Category
import com.example.domain.entities.remote.products.Product
import javax.inject.Inject


class PlatzyCategoryProvider @Inject constructor(
    private val api: PlatzyApi
) : CategoryProvider {

    override suspend fun getCategories() = api.getCategories().map {
        Category(
            id = it.id.toString(), name = it.name
        )
    }

    override suspend fun getProductsByCategory(category: String): List<Product> =
        api.getProductsByCategory(category).map { it.toDomain() }

}