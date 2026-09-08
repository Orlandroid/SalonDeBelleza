package com.example.data.remote.fake_store

import com.example.data.api.FakeStoreService
import com.example.data.database.daos.CategoriesDao
import com.example.data.database.mappers.toListCategoriesString
import com.example.data.database.mappers.toStringList
import com.example.data.products.fakestore.FakeStoreProduct
import com.example.domain.entities.Cart

class FakeStoreRepositoryImp(
    private val api: FakeStoreService,
    private val categoriesDao: CategoriesDao
) : FakeStoreRepository {

    override suspend fun getProducts(category: String): List<FakeStoreProduct> {
        return api.getProducts(categoria = category)
    }

    override suspend fun getSingleProduct(id: Int): FakeStoreProduct {
        return api.getSingleProduct(id = id)
    }

    override suspend fun getCategories(): List<String> {
        val listOfCategoriesFromLocalSource = categoriesDao.getCategories()
        return if (listOfCategoriesFromLocalSource.isEmpty()) {
            val categories = api.getCategories()
            categoriesDao.addManyCategories(categories.toListCategoriesString())
            api.getCategories()
        } else {
            categoriesDao.getCategories().toStringList()
        }
    }

    override suspend fun getSingleCart(id: Int): Cart {
        return api.getSingleCart(id = id)
    }
}