package com.example.domain.usecases

import com.example.domain.UserPreferences
import com.example.domain.entities.remote.User
import com.example.domain.repository.AuthRepository
import com.example.domain.repository.UserRepository
import com.example.model.state.ApiResult
import com.example.model.state.getContent
import com.example.model.state.getErrorMessage
import com.example.model.state.isError
import com.example.model.state.isSuccess
import com.example.model.state.models.UserRole
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import javax.inject.Inject

class SignInWithGoogleUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val userPreferences: UserPreferences,
    private val userRepository: UserRepository,
    private val firebaseAuth: FirebaseAuth
) {
    suspend operator fun invoke(idToken: String): ApiResult<UserRole> {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        val loginResult = authRepository.signInWithCredential(credential)

        if (loginResult.isError()) {
            return ApiResult.Error(loginResult.getErrorMessage())
        }

        val currentUser = firebaseAuth.currentUser
        val userResult = userRepository.getUser()

        val userRole = if (userResult.isSuccess()) {
            userResult.getContent().role
        } else {
            if (currentUser != null) {
                val newUser = User(
                    uid = currentUser.uid,
                    name = currentUser.displayName.orEmpty().ifEmpty { "Customer" },
                    phone = currentUser.phoneNumber.orEmpty(),
                    email = currentUser.email.orEmpty(),
                    password = "",
                    birthDay = "",
                    role = UserRole.CUSTOMER
                )
                userRepository.saveUserInfo(currentUser.uid, newUser)
            }
            UserRole.CUSTOMER
        }

        userPreferences.saveUserLogged()
        userPreferences.saveUserEmail(currentUser?.email.orEmpty())

        return ApiResult.Success(userRole)
    }
}
