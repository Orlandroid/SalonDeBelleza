package com.example.citassalon.presentacion.features.profile.userprofile

import com.example.domain.state.ApiResult
import com.google.firebase.database.DatabaseReference
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Named


class SaveUserImageUseCase @Inject constructor(
    @param:Named("imageUser")
    private val databaseReference: DatabaseReference
) {

    suspend fun saveImageUser(imageLikeBase64: String): ApiResult<Any> {
        try {
            databaseReference.setValue(imageLikeBase64).await()
            return ApiResult.Success(Any())
        } catch (e: Exception) {
            return ApiResult.Success(Any())
        }
    }
}
