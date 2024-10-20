package com.example.citassalon.presentacion.features.auth.sign_up

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.citassalon.presentacion.features.schedule_appointment.branches.BaseScreenState
import com.example.citassalon.presentacion.main.NetworkHelper
import com.example.citassalon.presentacion.util.isValidEmail
import com.example.domain.entities.remote.User
import com.google.firebase.database.FirebaseDatabase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
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


    var showErrorPhone = mutableStateOf(false)
    var showErrorEmail = mutableStateOf(false)
    var showErrorPassword = mutableStateOf(false)

    var birthDay = mutableStateOf("")
    var name = mutableStateOf("")
    var phone = mutableStateOf("")
    var email = mutableStateOf("")
    var password = mutableStateOf("")

    private val _state: MutableStateFlow<BaseScreenState<Unit>> =
        MutableStateFlow(BaseScreenState.Success(Unit))
    val state = _state.asStateFlow()

    private val _uiState: MutableStateFlow<SignUpUiState> =
        MutableStateFlow(SignUpUiState())
    val uiState = _uiState.asStateFlow()


    fun sinUpV2(email: String, password: String) {
        _state.value = BaseScreenState.Loading()
        if (networkHelper.isNetworkConnected()) {
            repository.register(email, password).addOnCompleteListener {
                if (it.isSuccessful) {
                    _state.value = BaseScreenState.Success(Unit)
                } else {
                    val error = Exception(it.exception?.message ?: "")
                    _state.value = BaseScreenState.Error(exception = error)
                }
            }
        } else {
            _state.value = BaseScreenState.ErrorNetwork()
        }
    }

    fun saveUserInformation(user: User) {
        if (!networkHelper.isNetworkConnected()) {
            return
        }
        val userUii = repository.getUser()?.uid ?: return
        Log.w("Usuario", userUii)
        firebaseDatabase.getReference("users").child(userUii).setValue(user)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    Log.w("Usuario", "Usuario registraro correctamnete")
                } else {
                    Log.w("Usuario", "Error al registar al usuario")
                }
            }
    }

    private fun isValidPassword(): Boolean {
        val passwordLength = password.value.trim().length
        return passwordLength > MINIMAL_CHARACTERS_PASSWORD
    }

    private fun getEmail(): String = email.value

    private fun isValidNumber(): Boolean = phone.value.trim().length == PHONE_NUMBER_CHARACTERS

    private fun isTheEmailValidEmail(email: String): Boolean {
        return isValidEmail(email)
    }

    private fun areEmptyFields(): Boolean {
        val nameIsEmpty = name.value.trim().isEmpty()
        val phoneIsEmpty = phone.value.trim().isEmpty()
        val emailIsEmpty = email.value.trim().isEmpty()
        val passwordIsEmpty = password.value.trim().isEmpty()
        val birthDayIsEmpty = birthDay.value.trim().isEmpty()
        return nameIsEmpty or phoneIsEmpty or emailIsEmpty or passwordIsEmpty or birthDayIsEmpty
    }

    private fun resetErrorsInputs() {
        showErrorPhone.value = false
        showErrorEmail.value = false
        showErrorPassword.value = false
    }

    fun validateForm(): Boolean {
        resetErrorsInputs()
        val passwordText = password.value.trim()
        val email = email.value.trim()
        val phone = phone.value.trim()
        var isValidPhone = true
        var isValidEmail = true
        var isValidPassword = true
        var areEmptyFields = false
        if (!isValidNumber()) {
            if (phone.isNotEmpty()) {
                showErrorPhone.value = true
            }
            isValidPhone = false
        }
        if (areEmptyFields()) areEmptyFields = true
        if (!isValidPassword()) {
            if (passwordText.isNotEmpty()) {
                showErrorPassword.value = true
            }
            isValidPassword = false
        }
        if (!isTheEmailValidEmail(getEmail())) {
            if (email.isNotEmpty()) {
                showErrorEmail.value = true
            }
            isValidEmail = false
        }
        return isValidPhone && isValidEmail && isValidPassword && !areEmptyFields
    }


}