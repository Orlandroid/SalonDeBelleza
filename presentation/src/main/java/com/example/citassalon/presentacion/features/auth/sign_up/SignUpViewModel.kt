package com.example.citassalon.presentacion.features.auth.sign_up

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.citassalon.presentacion.features.base.BaseScreenState
import com.example.citassalon.presentacion.main.NetworkHelper
import com.example.citassalon.presentacion.util.isValidEmail
import com.example.domain.entities.remote.User
import com.google.firebase.database.FirebaseDatabase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val repository: com.example.data.Repository,
    private val firebaseDatabase: FirebaseDatabase,
    private val networkHelper: NetworkHelper
) : ViewModel() {

    companion object {
        private const val MINIMAL_CHARACTERS_PASSWORD = 5
        private const val PHONE_NUMBER_CHARACTERS = 10
    }


    private val _state: MutableStateFlow<BaseScreenState<Unit>> =
        MutableStateFlow(BaseScreenState.Success(Unit))
    val state = _state.asStateFlow()

    private val _uiState: MutableStateFlow<SignUpUiState> = MutableStateFlow(SignUpUiState())
    val uiState = _uiState.asStateFlow()

    private val _effect = MutableStateFlow<SideEffects?>(null)
    val effect: Flow<SideEffects?> = _effect

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
                _uiState.update { oldState ->
                    oldState.copy(name = event.name)
                }
                validateForm()
            }

            is SingUpEvents.OnPhoneChange -> {
                _uiState.update { oldState ->
                    oldState.copy(phone = event.phone)
                }
                validateForm()
            }

            is SingUpEvents.OnEmailChange -> {
                _uiState.update { oldState ->
                    oldState.copy(email = event.email)
                }
                validateForm()
            }

            is SingUpEvents.OnPasswordChange -> {
                _uiState.update { oldState ->
                    oldState.copy(password = event.password)
                }
                validateForm()
            }

            is SingUpEvents.OnBirthDayChange -> {
                _uiState.update { oldState ->
                    oldState.copy(birthday = event.birthday)
                }
                validateForm()
            }


            is SingUpEvents.OnSignUpClick -> {
                sinUp(
                    user = _uiState.value.getUser()
                )
            }

        }
    }


    private fun sinUp(user: User) {
        saveUserInformation(user)
        _state.value = BaseScreenState.Loading()
        if (networkHelper.isNetworkConnected()) {
            repository.register(user.email, user.password).addOnCompleteListener {
                if (it.isSuccessful) {
                    Log.w("Usuario", "Succes")
                    _effect.value = SideEffects.OnLoginSuccess
                } else {
                    Log.w("Usuario", "error")
                    val error = Exception(it.exception?.message ?: "")
                    _state.value = BaseScreenState.Error(exception = error)
                }
            }
        } else {
            _state.value = BaseScreenState.ErrorNetwork()
        }
    }

    private fun saveUserInformation(user: User) {
        if (!networkHelper.isNetworkConnected()) {
            return
        }
        val userUii = repository.getUser()?.uid ?: return
        Log.w("Usuario", userUii)
        firebaseDatabase.getReference("users").child(userUii).setValue(user).addOnCompleteListener {
            if (it.isSuccessful) {
                Log.w("Usuario", "Usuario registraro correctamnete")
            } else {
                Log.w("Usuario", "Error al registar al usuario")
            }
        }
    }

    private fun isValidPassword(): Boolean {
        val passwordLength = uiState.value.password.trim().length
        return passwordLength > MINIMAL_CHARACTERS_PASSWORD
    }

    private fun getEmail(): String = uiState.value.email

    private fun isValidNumber(): Boolean =
        uiState.value.phone.trim().length == PHONE_NUMBER_CHARACTERS

    private fun isTheEmailValidEmail(email: String): Boolean {
        return isValidEmail(email)
    }

    private fun areEmptyFields(): Boolean {
        val nameIsEmpty = uiState.value.name.trim().isEmpty()
        val phoneIsEmpty = uiState.value.phone.trim().isEmpty()
        val emailIsEmpty = uiState.value.email.trim().isEmpty()
        val passwordIsEmpty = uiState.value.password.trim().isEmpty()
        val birthDayIsEmpty = uiState.value.birthday.trim().isEmpty()
        return nameIsEmpty or phoneIsEmpty or emailIsEmpty or passwordIsEmpty or birthDayIsEmpty
    }

    private fun resetErrorsInputs() {
        _uiState.update {
            it.copy(
                showErrorPassword = false,
                showErrorEmail = false,
                showErrorPhone = false,
                isEnableButton = false
            )
        }
    }

    private fun validateForm(): Boolean {
        resetErrorsInputs()
        val passwordText = uiState.value.password.trim()
        val email = uiState.value.email.trim()
        val phone = uiState.value.phone.trim()
        var isValidPhone = true
        var isValidEmail = true
        var isValidPassword = true
        var areEmptyFields = false
        if (!isValidNumber()) {
            if (phone.isNotEmpty()) {
                _uiState.update { it.copy(showErrorPhone = true) }
            }
            isValidPhone = false
        }
        if (areEmptyFields()) areEmptyFields = true
        if (!isValidPassword()) {
            if (passwordText.isNotEmpty()) {
                _uiState.update { it.copy(showErrorPassword = true) }
            }
            isValidPassword = false
        }
        if (!isTheEmailValidEmail(getEmail())) {
            if (email.isNotEmpty()) {
                _uiState.update { it.copy(showErrorEmail = true) }
            }
            isValidEmail = false
        }
        _uiState.update { it.copy(isEnableButton = true) }
        return isValidPhone && isValidEmail && isValidPassword && !areEmptyFields
    }


}