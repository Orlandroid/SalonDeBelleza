package com.example.data.remote.products.fakestore

import com.example.data.remote.products.commons.ProductProvider
import javax.inject.Inject

class FakeStoreProductProvider @Inject constructor(
    private val api: FakeStoreApi
) : ProductProvider {
    override suspend fun getProducts() = api.getProducts().map { it.toDomain() }

    override suspend fun getSingleProduct(id: Int) = api.getSingleProduct(id).toDomain()
}