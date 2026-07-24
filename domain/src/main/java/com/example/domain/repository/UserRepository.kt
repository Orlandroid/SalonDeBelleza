package com.example.domain.repository

import com.example.domain.entities.remote.User
import com.example.domain.perfil.UserInfoFirebase
import com.example.domain.state.ApiResult

interface UserRepository {

    suspend fun getNameAndPhone(): ApiResult<UserInfoFirebase>

    suspend fun getUserImage(): ApiResult<String>

    suspend fun saveUserImage(imageLikeBase64: String): ApiResult<Any>

    suspend fun saveUserInfo(
        userId: String,
        user: User
    ): ApiResult<Any>

    suspend fun getUserMoney(): ApiResult<String>

}