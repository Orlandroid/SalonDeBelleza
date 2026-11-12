package com.example.domain.usecases

import com.example.domain.entities.remote.User
import com.example.domain.repository.UserRepository
import com.example.model.state.ApiResult
import com.example.model.state.isError
import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject

class SaveUserInformationUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val firebaseAuth: FirebaseAuth
) {
    suspend operator fun invoke(user: User): ApiResult<Unit> {
        val userUid = user.uid.ifEmpty { firebaseAuth.uid.orEmpty() }

        if (userUid.isEmpty()) {
            return ApiResult.Error("User UID is missing")
        }

        val userToSave = user.copy(uid = userUid)
        val userInfoResult = userRepository.saveUserInfo(
            userId = userUid,
            user = userToSave
        )

        if (userInfoResult.isError()) {
            return ApiResult.Error("Error saving user information")
        }

        return ApiResult.Success(Unit)
    }
}
