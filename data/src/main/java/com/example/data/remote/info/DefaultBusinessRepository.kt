package com.example.data.remote.info

import com.example.data.api.WebServices
import com.example.data.database.daos.ProductDao
import com.example.data.database.mappers.toProduct
import com.example.data.database.mappers.toProductEntity
import com.example.domain.entities.remote.dummyUsers.User
import com.example.domain.entities.remote.products.Product
import com.example.domain.repository.BusinessRepository
import com.example.domain.state.ApiResult
import javax.inject.Inject

class DefaultBusinessRepository @Inject constructor(
    private val productDao: ProductDao,
    private val webServices: WebServices
) :
    BusinessRepository {

    companion object {
        private const val NOT_SAVE = -1
    }

    override suspend fun getAllProducts(): ApiResult<List<Product>> {
        return runCatching {
            productDao.getAllProductDb().map { it.toProduct() }
        }.fold(
            onSuccess = {
                ApiResult.Success(it)
            },
            onFailure = {
                ApiResult.Error(it.message)
            }
        )
    }

    override suspend fun deleteAllProducts(): ApiResult<Unit> {
        val deletedRows = productDao.deleteAll()
        return if (deletedRows > 0) {
            ApiResult.Success(Unit)
        } else {
            ApiResult.Error("No products were deleted")
        }
    }

    override suspend fun getStaffUsers(): ApiResult<List<User>> {
        val resultStaffs = runCatching { webServices.getStaffUsers("https://dummyjson.com/users") }
        if (resultStaffs.isSuccess) {
            val response = resultStaffs.getOrNull()?.users
            return ApiResult.Success(result = response ?: emptyList())
        } else {
            return ApiResult.Error(error = resultStaffs.exceptionOrNull()?.message)
        }
    }

    override suspend fun addProduct(product: Product): ApiResult<Unit> {
        return runCatching {
            productDao.addProductDb(product.toProductEntity())
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


}