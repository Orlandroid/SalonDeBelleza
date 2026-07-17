package com.example.data


import com.example.data.database.local.LocalDataSource
import com.example.data.database.mappers.toProduct
import com.example.data.database.mappers.toProductEntity
import com.example.domain.RemoteDataSource
import com.example.domain.entities.remote.products.Product
import com.example.domain.state.ApiResult
import javax.inject.Inject

class Repository @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
) {

    suspend fun addProduct(product: Product) = localDataSource.addProduct(product.toProductEntity())


    suspend fun deleteAllProducts(): ApiResult<Unit> {
        val deletedRows = localDataSource.deleteAllProducts()
        return if (deletedRows > 0) {
            ApiResult.Success(Unit)
        } else {
            ApiResult.Error("No products were deleted")
        }
    }

    fun getAllProducts(): List<Product> {
        return localDataSource.getAllProducts().map { it.toProduct() }
    }


    suspend fun getStaffUsers() = remoteDataSource.getStaffUsers()

}