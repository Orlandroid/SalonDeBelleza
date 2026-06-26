package com.example.data.remote


import com.example.data.api.FakeStoreService
import com.example.data.api.WebServices
import com.example.domain.RemoteDataSource
import com.example.domain.entities.remote.Cart
import com.example.domain.entities.remote.Product
import com.example.domain.entities.remote.Service
import com.example.domain.entities.remote.Staff
import com.example.domain.entities.remote.dummyUsers.User
import com.example.domain.entities.remote.migration.SucursalesResponse
import com.example.domain.perfil.RandomUserResponse
import com.example.domain.state.ApiResult
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteDataSourceImpl @Inject constructor(
    private val fakeStoreService: FakeStoreService,
    private val webServices: WebServices
) : RemoteDataSource {
    override suspend fun getProducts(category: String): List<Product> =
        fakeStoreService.getProducts(category)


    override suspend fun getSingleCart(id: Int): Cart = fakeStoreService.getSingleCart(id)

    override suspend fun getBranches(): SucursalesResponse = webServices.getSucursales()

    override suspend fun getStaffs(): List<Staff> = webServices.getStaff()

    override suspend fun getServices(): List<Service> = webServices.getServicios()

    override suspend fun randomUser(): RandomUserResponse =
        webServices.randomUser("https://randomuser.me/api/")

    override suspend fun getStaffUsers(): ApiResult<List<User>> {
        val resultStaffs = runCatching { webServices.getStaffUsers("https://dummyjson.com/users") }
        if (resultStaffs.isSuccess) {
            val response = resultStaffs.getOrNull()?.users
            return ApiResult.Success(result = response ?: emptyList())
        } else {
            return ApiResult.Error(error = resultStaffs.exceptionOrNull()?.message)
        }
    }

}