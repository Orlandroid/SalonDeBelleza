package com.example.data.remote.user

import com.example.data.di.UsersRef
import com.example.domain.entities.remote.User
import com.example.domain.perfil.UserInfoFirebase
import com.example.domain.state.ApiResult
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import kotlin.coroutines.resume

class UserRepositoryImpl @Inject constructor(
    @param:UsersRef private val databaseReference: DatabaseReference
) :
    UserRepository {

    override suspend fun getNameAndPhone(): ApiResult<UserInfoFirebase> =
        suspendCancellableCoroutine { continuation ->
            databaseReference.addListenerForSingleValueEvent(
                object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {

                        val userInfo = snapshot.getValue(UserInfoFirebase::class.java)

                        if (userInfo == null) {
                            continuation.resume(ApiResult.Error(""))
                        }
                        continuation.resume(
                            ApiResult.Success(
                                userInfo!!
                            )
                        )
                    }

                    override fun onCancelled(error: DatabaseError) {
                        continuation.resume(ApiResult.Error(error.toException().toString()))
                    }
                }
            )
        }

    override suspend fun getUserImage(): ApiResult<String> {
        try {
            val snapshot = databaseReference.get().await()
            val userImage: String = snapshot.getValue(String::class.java) ?: ""
            return ApiResult.Success(userImage)
        } catch (e: Exception) {
            return ApiResult.Error(e.message ?: "Unknown error")
        }
    }

    override suspend fun saveUserImage(imageLikeBase64: String): ApiResult<Any> {
        try {
            databaseReference.setValue(imageLikeBase64).await()
            return ApiResult.Success(Any())
        } catch (e: Exception) {
            return ApiResult.Error(e.message)
        }
    }

    override suspend fun saveUserInfo(
        userId: String,
        user: User
    ): ApiResult<Any> {
        return try {
            databaseReference.child(userId).setValue(user)
            ApiResult.Success(Any())
        } catch (e: Exception) {
            ApiResult.Error(error = e.message.orEmpty())
        }
    }

}