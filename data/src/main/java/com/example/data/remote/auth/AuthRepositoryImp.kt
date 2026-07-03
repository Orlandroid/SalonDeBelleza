package com.example.data.remote.auth


import com.example.domain.state.ApiResult
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.tasks.await

class AuthRepositoryImp(
    private val firebaseAuth: FirebaseAuth
) : AuthRepository {
    override fun getUser(): ApiResult<FirebaseUser?> {
        val firebaseUser = firebaseAuth.currentUser
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
            firebaseAuth.signInWithEmailAndPassword(email, password).await()
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
            firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            ApiResult.Success(Unit)
        } catch (e: Exception) {
            ApiResult.Error(e.message ?: "error registering user")
        }
    }

    override suspend fun forgetPassword(email: String): ApiResult<Unit> {
        return try {
            firebaseAuth.sendPasswordResetEmail(email).await()
            ApiResult.Success(Unit)
        } catch (e: Exception) {
            ApiResult.Error(e.message ?: "An error occurred")
        }
    }

    override suspend fun signInWithCredential(credential: AuthCredential): ApiResult<Unit> {
        return try {
            firebaseAuth.signInWithCredential(credential).await()
            ApiResult.Success(Unit)
        } catch (e: Exception) {
            ApiResult.Error(e.message ?: "An error occurred")
        }
    }

    override suspend fun logout(): ApiResult<Unit> {
        return try {
            firebaseAuth.signOut()
            ApiResult.Success(Unit)
        } catch (e: Exception) {
            ApiResult.Error(e.message ?: "An error occurred")
        }
    }


}