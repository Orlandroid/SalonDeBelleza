package com.example.data.remote.fake_store

import com.example.data.api.FakeStoreService
import com.example.domain.LocalDataSource
import com.example.domain.entities.remote.Cart
import com.example.data.remote.products.fakestore.FakeStoreProduct
import com.example.domain.mappers.toListCategoriesString
import com.example.domain.mappers.toStringList

class FakeStoreRepositoryImp(
    private val api: FakeStoreService,
    private val localDataSource: LocalDataSource
) : FakeStoreRepository {

    override suspend fun getProducts(category: String): List<FakeStoreProduct> {
        return api.getProducts(categoria = category)
    }

    override suspend fun getSingleProduct(id: Int): FakeStoreProduct {
        return api.getSingleProduct(id = id)
    }

    override suspend fun getCategories(): List<String> {
        val listOfCategoriesFromLocalSource = localDataSource.getCategoriesFromDb()
        return if (listOfCategoriesFromLocalSource.isEmpty()) {
            val categories = api.getCategories()
            localDataSource.addManyCategories(categories.toListCategoriesString())
            api.getCategories()
        } else {
            localDataSource.getCategoriesFromDb().toStringList()
        }
    }

    override suspend fun getSingleCart(id: Int): Cart {
        return api.getSingleCart(id = id)
    }
}