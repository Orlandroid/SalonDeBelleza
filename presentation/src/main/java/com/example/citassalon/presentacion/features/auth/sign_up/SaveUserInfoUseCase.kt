package com.example.citassalon.presentacion.features.auth.sign_up

import com.example.domain.entities.remote.User
import com.example.domain.state.ApiResult
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class SaveUserInfoUseCase @Inject constructor(
    private val firebaseDatabase: FirebaseDatabase
) {

    suspend fun invoke(
        userId: String,
        user: User
    ): ApiResult<Any> {
        return try {
            firebaseDatabase.getReference("users").child(userId).setValue(user).await()
            ApiResult.Success(Any())
        } catch (e: Exception) {
            ApiResult.Error(error = e.message.orEmpty())
        }
    }

}