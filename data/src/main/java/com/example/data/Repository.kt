package com.example.data


import com.example.domain.LocalDataSource
import com.example.domain.RemoteDataSource
import com.example.domain.entities.db.ProductDb
import com.example.domain.state.ApiResult
import javax.inject.Inject

class Repository @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
) {

    suspend fun addProduct(productDb: ProductDb) = localDataSource.addProduct(productDb)

    suspend fun deleteAllProducts(): ApiResult<Unit> {
        val deletedRows = localDataSource.deleteAllProducts()
        return if (deletedRows > 0) {
            ApiResult.Success(Unit)
        } else {
            ApiResult.Error("No products were deleted")
        }
    }

    fun getAllProducts() = localDataSource.getAllProducts()


    suspend fun getStaffUsers() = remoteDataSource.getStaffUsers()

}