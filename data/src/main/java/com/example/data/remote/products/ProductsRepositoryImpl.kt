package com.example.data.remote.products

import com.example.data.api.FakeStoreService
import javax.inject.Inject

class ProductsRepositoryImpl @Inject constructor(
    private val fakeStoreService: FakeStoreService,
) : ProductsRepository {
    override suspend fun getProducts(category: String) = fakeStoreService.getProducts(category)

}