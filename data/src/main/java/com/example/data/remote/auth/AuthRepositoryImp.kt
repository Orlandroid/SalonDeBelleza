package com.example.data.remote.auth


import com.example.data.firebase.FireBaseSource
import com.example.domain.state.ApiResult
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.tasks.await

class AuthRepositoryImp(
    private val fireBaseSource: FireBaseSource
) : AuthRepository {
    override fun getUser(): ApiResult<FirebaseUser?> {
        val firebaseUser = fireBaseSource.getUser()
        if (firebaseUser != null) {
            return ApiResult.Success(firebaseUser)
        }
        return ApiResult.Error("User not found")
    }

    override suspend fun login(
        email: String,
        password: String
    ): ApiResult<Unit> {
        return try {
            fireBaseSource.login(email = email, password = password).await()
            ApiResult.Success(Unit)
        } catch (e: Exception) {
            ApiResult.Error(e.message ?: "An error occurred")
        }
    }

    override suspend fun register(
        email: String,
        password: String
    ): ApiResult<Unit> {
        return try {
            fireBaseSource.register(email = email, password = password).await()
            ApiResult.Success(Unit)
        } catch (e: Exception) {
            ApiResult.Error(e.message ?: "An error occurred")
        }
    }

    override suspend fun forgetPassword(email: String): ApiResult<Unit> {
        return try {
            fireBaseSource.forgetPassword(email = email).await()
            ApiResult.Success(Unit)
        } catch (e: Exception) {
            ApiResult.Error(e.message ?: "An error occurred")
        }
    }

    override suspend fun signInWithCredential(credential: AuthCredential): ApiResult<Unit> {
        return try {
            fireBaseSource.signInWithCredential(credential = credential)
            ApiResult.Success(Unit)
        } catch (e: Exception) {
            ApiResult.Error(e.message ?: "An error occurred")
        }
    }

    override suspend fun logout(): ApiResult<Unit> {
        return try {
            fireBaseSource.logout()
            ApiResult.Success(Unit)
        } catch (e: Exception) {
            ApiResult.Error(e.message ?: "An error occurred")
        }
    }


}