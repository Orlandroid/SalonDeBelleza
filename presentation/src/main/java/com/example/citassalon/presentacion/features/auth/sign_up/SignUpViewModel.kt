package com.example.citassalon.presentacion.features.auth.sign_up

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.citassalon.presentacion.features.extensions.dateFormat
import com.example.citassalon.presentacion.features.extensions.getCurrentDateTime
import com.example.citassalon.presentacion.features.extensions.toStringFormat
import com.example.domain.entities.remote.User
import com.google.firebase.database.FirebaseDatabase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class SingUpEvents {
    data object OnSignUpClick : SingUpEvents()
    data class OnNameChange(val name: String) : SingUpEvents()
    data class OnPhoneChange(val phone: String) : SingUpEvents()
    data class OnEmailChange(val email: String) : SingUpEvents()
    data class OnPasswordChange(val password: String) : SingUpEvents()
    object OnOpenDatePick : SingUpEvents()
    object OnCloseDatePicker : SingUpEvents()
    data class OnDateSelected(val birthday: String) : SingUpEvents()
}

sealed class SignUpSideEffects {
    data object NavigateToLoginScreen : SignUpSideEffects()
}

data class SignUpUiState(
    val name: String = "",
    val phone: String = "",
    val email: String = "",
    val password: String = "",
    val birthday: String = getCurrentDateTime().toStringFormat(dateFormat),
    var showErrorPhone: Boolean = false,
    var showErrorEmail: Boolean = false,
    var showErrorPassword: Boolean = false,
    var isEnableButton: Boolean = false,
    val isLoading: Boolean = false,
    val error: Exception? = null,
    val showDatePicker: Boolean = false
)


@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val repository: com.example.data.Repository,
    private val firebaseDatabase: FirebaseDatabase,
    private val useCaseValidateForm: UseCaseValidateFormSignUp
) : ViewModel() {


    private val _state: MutableStateFlow<SignUpUiState> = MutableStateFlow(SignUpUiState())
    val state = _state.asStateFlow()


    private val _effects = Channel<SignUpSideEffects>()

    val effects = _effects.receiveAsFlow()

    private fun SignUpUiState.getUser(): User {
        return User(
            name = name,
            phone = phone,
            email = email,
            password = password,
            birthDay = birthday
        )
    }

    fun onEvents(event: SingUpEvents) {
        when (event) {
            is SingUpEvents.OnNameChange -> {
                _state.update { it.copy(name = event.name) }
                validateForm()
            }

            is SingUpEvents.OnPhoneChange -> {
                _state.update { it.copy(phone = event.phone) }
                validateForm()
            }

            is SingUpEvents.OnEmailChange -> {
                _state.update { it.copy(email = event.email) }
                validateForm()
            }

            is SingUpEvents.OnPasswordChange -> {
                _state.update { it.copy(password = event.password) }
                validateForm()
            }

            is SingUpEvents.OnSignUpClick -> {
                sinUp()
            }

            SingUpEvents.OnOpenDatePick -> {
                _state.update { it.copy(showDatePicker = true) }
            }

            SingUpEvents.OnCloseDatePicker -> {
                _state.update { it.copy(showDatePicker = false) }
            }

            is SingUpEvents.OnDateSelected -> {
                _state.update { it.copy(birthday = event.birthday) }
                validateForm()
            }
        }
    }


    private fun sinUp() {
        val user = _state.value.getUser()
        _state.update { it.copy(isLoading = true) }
        repository.register(user.email, user.password).addOnCompleteListener {
            if (it.isSuccessful) {
                saveUserInformation(user)
                sendEffect(SignUpSideEffects.NavigateToLoginScreen)
            } else {
                val error = Exception(it.exception?.message ?: "")
                _state.update { state -> state.copy(error = error) }
            }
            _state.update { state -> state.copy(isLoading = false) }
        }

    }

    private fun saveUserInformation(userP: User) {
        val user = repository.getUser()?.uid ?: return
        firebaseDatabase.getReference("users").child(user).setValue(userP).addOnCompleteListener {
            if (it.isSuccessful) {
                //Send one snackbar or some info to one dialog for show success
            } else {
                //Send one snackbar or some info to one dialog for show error
            }
        }
    }

    private fun sendEffect(effect: SignUpSideEffects) {
        viewModelScope.launch {
            _effects.send(effect)
        }
    }


    private fun resetErrorsInputs() {
        _state.update {
            it.copy(
                showErrorPassword = false,
                showErrorEmail = false,
                showErrorPhone = false,
                isEnableButton = false
            )
        }
    }

    private fun validateForm() {
        resetErrorsInputs()
        val resultForm = useCaseValidateForm.invoke(
            birthDay = state.value.birthday,
            name = state.value.name,
            password = state.value.password,
            email = state.value.email,
            phone = state.value.phone
        )
        _state.update {
            it.copy(
                showErrorPhone = !resultForm.isValidPhone,
                showErrorPassword = !resultForm.isValidPassword,
                showErrorEmail = !resultForm.isValidEmail,
                isEnableButton = resultForm.isFormValid
            )
        }
    }


}