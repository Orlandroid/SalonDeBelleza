package com.example.data.products.fakestore

import com.example.data.products.commons.category.CategoryProvider
import com.example.data.products.commons.product.ProductProvider
import com.example.domain.entities.products.Category
import javax.inject.Inject

class FakeStoreProductProvider @Inject constructor(
    private val api: FakeStoreApi
) : ProductProvider, CategoryProvider {
    override suspend fun getProducts() = api.getProducts().map { it.toDomain() }

    override suspend fun getSingleProduct(id: Int) = api.getSingleProduct(id).toDomain()

    override suspend fun getCategories() = api.getCategories().map { Category(id = it, name = it) }
    override suspend fun getProductsByCategory(category: String) =
        api.getProducts(categoria = category).map { it.toDomain() }

}