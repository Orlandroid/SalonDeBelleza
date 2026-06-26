package com.example.domain.use_cases

import com.example.domain.perfil.UserInfoFirebase
import com.example.domain.state.ApiResult
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import javax.inject.Named
import kotlin.coroutines.resume

class GetNameAndPhoneUseCase @Inject constructor(
    @param:Named("users")
    private val databaseReference: DatabaseReference
) {


    suspend fun invoke(): ApiResult<UserInfoFirebase> =
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

}