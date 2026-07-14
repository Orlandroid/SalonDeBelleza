package com.example.data.remote.products.fakestore

import com.example.data.remote.products.commons.category.CategoryProvider
import com.example.domain.entities.remote.products.Category
import javax.inject.Inject


class FakeStoreCategoryProvider @Inject constructor(
    private val api: FakeStoreApi
) : CategoryProvider {

    override suspend fun getCategories() = api.getCategories().map { Category(id = it, name = it) }
    override suspend fun getProductsByCategory(category: String) = api.getProducts(categoria = category).map { it.toDomain() }

}