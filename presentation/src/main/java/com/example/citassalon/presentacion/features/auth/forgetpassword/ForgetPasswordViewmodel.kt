package com.example.citassalon.presentacion.features.auth.forgetpassword

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.citassalon.presentacion.util.EmailValidator
import com.example.citassalon.presentacion.util.isValidEmail
import com.example.data.di.IoDispatcher
import com.example.data.remote.auth.AuthRepository
import com.example.domain.state.isSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ForgetPasswordViewmodel @Inject constructor(
    private val authRepository: AuthRepository,
    @param:IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val emailValidator: EmailValidator
) : ViewModel() {


    sealed class ForgetPasswordEvents {
        data class OnEmailChange(val email: String) : ForgetPasswordEvents()
        object OnResetPassword : ForgetPasswordEvents()
    }

    sealed class ForgetPasswordEffects {
        data class ShowSnackBar(val message: String) : ForgetPasswordEffects()
    }

    data class ForgetPasswordUiState(
        val userEmail: String? = null,
        val enableButton: Boolean = false,
        val showErrorInvalidEmail: Boolean = false,
        val isLoading: Boolean = false
    )

    private val _state: MutableStateFlow<ForgetPasswordUiState> =
        MutableStateFlow(ForgetPasswordUiState())
    val state = _state.asStateFlow()

    private val _effects = Channel<ForgetPasswordEffects>()
    val effects = _effects.receiveAsFlow()

    fun onEvents(event: ForgetPasswordEvents) {
        when (event) {
            is ForgetPasswordEvents.OnResetPassword -> {
                _state.update { it.copy(isLoading = true) }
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
        if (!emailValidator.isValidEmail(email)) {
            _state.update { it.copy(showErrorInvalidEmail = true) }
            return
        }
        _state.update { it.copy(enableButton = true) }
    }

    private fun forgetPassword(email: String) {
        viewModelScope.launch(ioDispatcher) {
            val result = authRepository.forgetPassword(email)
            if (result.isSuccess()) {
                sendEffect(ForgetPasswordEffects.ShowSnackBar(message = "Password successful changed"))
            } else {
                sendEffect(ForgetPasswordEffects.ShowSnackBar(message = "Error trying to updated password"))
            }
            _state.update { state -> state.copy(isLoading = false) }
        }
    }

    private fun sendEffect(effect: ForgetPasswordEffects) {
        viewModelScope.launch(ioDispatcher) {
            _effects.send(effect)
        }
    }

}