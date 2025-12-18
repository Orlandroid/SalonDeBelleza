package com.example.citassalon.presentacion.features.auth.forgetpassword

import androidx.lifecycle.ViewModel
import com.example.citassalon.presentacion.util.isValidEmail
import com.example.data.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject


@HiltViewModel
class ForgetPasswordViewmodel @Inject constructor(
    private val repository: Repository
) : ViewModel() {


    sealed class ForgetPasswordEvents {
        data class OnEmailChange(val email: String) : ForgetPasswordEvents()
        object OnResetPassword : ForgetPasswordEvents()
    }

    data class ForgetPasswordUiState(
        val userEmail: String? = null,
        val enableButton: Boolean = false,
        val showErrorInvalidEmail: Boolean = false
    )

    private val _state: MutableStateFlow<ForgetPasswordUiState> =
        MutableStateFlow(ForgetPasswordUiState())
    val state = _state.asStateFlow()

    fun onEvents(event: ForgetPasswordEvents) {
        when (event) {
            is ForgetPasswordEvents.OnResetPassword -> {
                forgetPassword(state.value.userEmail.toString())
            }

            is ForgetPasswordEvents.OnEmailChange -> {
                _state.update { it.copy(userEmail = event.email) }
                validateEmail(email = event.email)
            }
        }
    }

    private fun validateEmail(email: String) {
        _state.update { it.copy(showErrorInvalidEmail = false, enableButton = false) }
        if (!isValidEmail(email)) {
            _state.update { it.copy(showErrorInvalidEmail = true) }
            return
        }
        _state.update { it.copy(enableButton = true) }
    }

    private fun forgetPassword(email: String) {
        repository.forgetPassword(email).addOnCompleteListener {
            if (it.isSuccessful) {
                //Show one snack bar for show show the operation was sucesuful
            } else {
                //Show one snack bar for show show the operation din,t success
            }
        }
    }

}