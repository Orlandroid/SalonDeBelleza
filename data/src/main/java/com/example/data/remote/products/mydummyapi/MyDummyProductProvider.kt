package com.example.data.remote.products.mydummyapi

import com.example.data.remote.products.commons.ProductProvider
import com.example.data.remote.products.fakestore.toDomain
import javax.inject.Inject

class MyDummyProductProvider @Inject constructor(
    private val api: MyDummyApi
) : ProductProvider {
    override suspend fun getProducts() = api.getProducts().map { it.toDomain() }
}