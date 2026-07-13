package com.example.data.remote.products.fakestore

import com.example.data.remote.products.commons.category.CategoryProvider
import com.example.data.remote.products.commons.product.ProductProvider
import javax.inject.Inject
import com.example.domain.entities.remote.products.Category

class FakeStoreProductProvider @Inject constructor(
    private val api: FakeStoreApi
) : ProductProvider, CategoryProvider {
    override suspend fun getProducts() = api.getProducts().map { it.toDomain() }

    override suspend fun getSingleProduct(id: Int) = api.getSingleProduct(id).toDomain()
    override suspend fun getCategories() = api.getCategories().map { Category(id = it, name = it) }

}