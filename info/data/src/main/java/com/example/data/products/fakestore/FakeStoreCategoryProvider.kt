package com.example.data.products.fakestore

import com.example.data.products.commons.category.CategoryProvider
import com.example.domain.Product
import com.example.domain.entities.products.Category
import javax.inject.Inject


class FakeStoreCategoryProvider @Inject constructor(
    private val api: FakeStoreApi
) : CategoryProvider {

    override suspend fun getCategories() = api.getCategories().map { Category(id = it, name = it) }
    override suspend fun getProductsByCategory(category: String): List<Product> {
        return api.getProducts(categoria = category).map { it.toDomain() }
    }

}