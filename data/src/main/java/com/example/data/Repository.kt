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

    companion object {
        private const val NOT_SAVE = -1
    }


    suspend fun addProduct(product: Product): ApiResult<Unit> {
        return runCatching {
            localDataSource.addProduct(product.toProductEntity())
        }.fold(
            onSuccess = { id ->
                if (id == NOT_SAVE.toLong()) {
                    ApiResult.Error("Product could not be saved")
                } else {
                    ApiResult.Success(Unit)
                }
            },
            onFailure = {
                ApiResult.Error(it.message ?: "Unknown error")
            }
        )
    }


    suspend fun deleteAllProducts(): ApiResult<Unit> {
        val deletedRows = localDataSource.deleteAllProducts()
        return if (deletedRows > 0) {
            ApiResult.Success(Unit)
        } else {
            ApiResult.Error("No products were deleted")
        }
    }

    fun getAllProducts(): ApiResult<List<Product>> {
        return runCatching {
            localDataSource.getAllProducts().map { it.toProduct() }
        }.fold(
            onSuccess = {
                ApiResult.Success(it)
            },
            onFailure = {
                ApiResult.Error(it.message)
            }
        )
    }


    suspend fun getStaffUsers() = runCatching {
        remoteDataSource.getStaffUsers()
    }.fold(
        onSuccess = {
            ApiResult.Success(it)
        },
        onFailure = {
            ApiResult.Error(it.message)
        }
    )

}