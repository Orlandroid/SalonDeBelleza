package com.example.data.remote.user

import com.example.di.qualifiers.UsersRef
import com.example.domain.UserInfoFirebase
import com.example.domain.entities.remote.User
import com.example.domain.repository.UserRepository
import com.example.model.state.ApiResult
import com.example.model.state.models.UserRole
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.getValue
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class UserRepositoryImpl
    @Inject
    constructor(
        @param:UsersRef private val databaseReference: DatabaseReference,
        private val firebaseAuth: FirebaseAuth,
    ) : UserRepository {
        override suspend fun getUser(): ApiResult<User> {
            return runCatching {
                val userId =
                    firebaseAuth.uid
                        ?: return ApiResult.Error("User is not authenticated")

                val snapshot = databaseReference.child(userId).get().await()

                if (!snapshot.exists()) {
                    return ApiResult.Error("User not found")
                }

                val userInfo =
                    snapshot.getValue<UserInfoFirebase>()
                        ?: return ApiResult.Error("Unable to parse user info")

                val userRole =
                    if (userInfo.role == "ADMIN") {
                        UserRole.ADMIN
                    } else {
                        UserRole.CUSTOMER
                    }

                val user =
                    User(
                        name = userInfo.name,
                        phone = userInfo.phone,
                        email = userInfo.email,
                        password = userInfo.password,
                        birthDay = userInfo.birthDay,
                        role = userRole,
                        uid = userId,
                    )

                ApiResult.Success(user)
            }.getOrElse {
                ApiResult.Error(it.message.orEmpty())
            }
        }

        override suspend fun getUserImage(): ApiResult<String> =
            runCatching {
                val snapshot = databaseReference.get().await()
                val userImage: String = snapshot.getValue<String>() ?: ""
                ApiResult.Success(userImage)
            }.getOrElse {
                ApiResult.Error(it.message.orEmpty())
            }

        override suspend fun saveUserImage(imageLikeBase64: String): ApiResult<Any> =
            runCatching {
                databaseReference.setValue(imageLikeBase64).await()
                ApiResult.Success(Any())
            }.getOrElse {
                ApiResult.Error(it.message.orEmpty())
            }

        override suspend fun saveUserInfo(
            userId: String,
            user: User,
        ): ApiResult<Any> =
            runCatching {
                databaseReference.child(userId).setValue(user).await()
                ApiResult.Success(Any())
            }.getOrElse {
                ApiResult.Error(it.message.orEmpty())
            }
    }
