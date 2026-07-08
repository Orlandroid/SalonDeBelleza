package com.example.data.remote.products.dummyjson

import com.example.data.remote.products.commons.ProductProvider
import javax.inject.Inject

class DummyJsonProductProvider @Inject constructor(
    private val api: DummyJsonApiV2
) : ProductProvider {
    override suspend fun getProducts() = api.getProducts().toDomain()
}