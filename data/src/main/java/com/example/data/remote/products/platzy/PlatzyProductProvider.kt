package com.example.data.remote.products.platzy

import com.example.data.remote.products.commons.ProductProvider
import javax.inject.Inject

class PlatzyProductProvider @Inject constructor(
    private val api: PlatzyApi
) : ProductProvider {
    override suspend fun getProducts() = api.getProducts().map { it.toDomain() }
    override suspend fun getSingleProduct(id: Int) = api.getSingleProduct(id).toDomain()
}