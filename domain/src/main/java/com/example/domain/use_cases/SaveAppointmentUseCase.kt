package com.example.domain.use_cases

import com.example.domain.state.ApiResult
import com.google.firebase.database.DatabaseReference
import javax.inject.Inject
import javax.inject.Named


class SaveAppointmentUseCase @Inject constructor(
    @param:Named("imageUser")
    private val databaseReference: DatabaseReference
) {

    suspend fun invoke(imageLikeBase64: String): ApiResult<Any> {

        return runCatching {

        }.fold(
            onSuccess = { data ->
                ApiResult.Success(data)
            },
            onFailure = { throwable ->
                ApiResult.Error(throwable.message ?: "Unknown error")
            }
        )
    }
}
