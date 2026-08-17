package com.example.data.remote


import com.example.data.api.WebServices
import com.example.domain.RemoteDataSource
import com.example.domain.perfil.RandomUserResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteDataSourceImpl @Inject constructor(
    private val webServices: WebServices
) : RemoteDataSource {


    override suspend fun randomUser(): RandomUserResponse =
        webServices.randomUser("https://randomuser.me/api/")

}