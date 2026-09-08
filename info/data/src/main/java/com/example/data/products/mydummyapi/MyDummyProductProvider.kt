package com.example.data.products.mydummyapi

import com.example.data.products.commons.product.ProductProvider
import javax.inject.Inject

class MyDummyProductProvider @Inject constructor(
    private val api: MyDummyApi
) : ProductProvider {
    override suspend fun getProducts() = api.getProducts().map { it.toDomain() }
    override suspend fun getSingleProduct(id: Int) = api.getProducts().map { it.toDomain() }.first { it.id == id }
}