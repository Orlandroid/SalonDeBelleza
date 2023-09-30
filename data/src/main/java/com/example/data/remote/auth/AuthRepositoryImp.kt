package com.example.data.remote.auth


import com.example.data.api.DummyJsonApi
import com.example.domain.entities.remote.products.ProductResponse

class AuthRepositoryImp(private val api: DummyJsonApi) : AuthRepository {

    override suspend fun getProducts(): ProductResponse = api.getProducts()

    override suspend fun getSingleProduct(productId: Int) = api.getSingleProduct(productId)


}