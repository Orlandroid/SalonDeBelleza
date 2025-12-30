package com.example.citassalon.presentacion.features.profile.userprofile


import com.example.domain.state.ApiResult
import com.google.firebase.database.DatabaseReference
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Named

class GetUserImageUseCase @Inject constructor(
    @param:Named("imageUser")
    private val databaseReference: DatabaseReference
) {

    suspend fun invoke(): ApiResult<String> {
        try {
            val snapshot = databaseReference.get().await()
            val userImage: String = snapshot.getValue(String::class.java) ?: ""
            return ApiResult.Success(userImage)
        } catch (e: Exception) {
            return ApiResult.Error(e.message ?: "Unknown error")
        }
    }
}