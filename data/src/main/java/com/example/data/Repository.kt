package com.example.data


import com.example.domain.LocalDataSource
import com.example.domain.RemoteDataSource
import com.example.domain.entities.db.ProductDb
import com.example.domain.mappers.toListCategoriesString
import com.example.domain.mappers.toStringList
import javax.inject.Inject

class Repository @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
) {

    suspend fun addProduct(productDb: ProductDb) = localDataSource.addProduct(productDb)

    suspend fun deleteAllProducts() = localDataSource.deleteAllProducts()

    fun getAllProducts() = localDataSource.getAllProducts()


    suspend fun getSingleProduct(id: Int) = remoteDataSource.getSingleProduct(id)

    suspend fun getCategories(): List<String> {
        val listOfCategoriesFromLocalSource = localDataSource.getCategoriesFromDb()
        return if (listOfCategoriesFromLocalSource.isEmpty()) {
            val categories = remoteDataSource.getCategories()
            localDataSource.addManyCategories(categories.toListCategoriesString())
            remoteDataSource.getCategories()
        } else {
            localDataSource.getCategoriesFromDb().toStringList()
        }
    }


    suspend fun getSucursales() = remoteDataSource.getSucursales()


    suspend fun getStaffUsers() = remoteDataSource.getStaffUsers()

}