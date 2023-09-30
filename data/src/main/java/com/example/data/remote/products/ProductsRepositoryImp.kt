package com.example.data.remote.products


import com.example.data.api.DummyJsonApi
import com.example.domain.entities.remote.products.ProductResponse

class ProductsRepositoryImp(private val api: DummyJsonApi) : ProductsRepository {

    override suspend fun getProducts(): ProductResponse = api.getProducts()

    override suspend fun getSingleProduct(productId: Int) = api.getSingleProduct(productId)
    override suspend fun getCategories() = api.getCategories()


}