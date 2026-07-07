package com.example.data.remote


import com.example.data.api.WebServices
import com.example.domain.RemoteDataSource
import com.example.domain.entities.remote.dummyUsers.User
import com.example.domain.perfil.RandomUserResponse
import com.example.domain.state.ApiResult
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteDataSourceImpl @Inject constructor(
    private val webServices: WebServices
) : RemoteDataSource {


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