package com.example.citassalon.presentacion.features.auth.login


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.citassalon.presentacion.util.isValidEmail
import com.example.data.Repository
import com.example.data.preferences.LoginPreferences
import com.example.domain.state.SessionStatus
import com.google.firebase.auth.GoogleAuthProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds


sealed class LoginEvents {
    data class OnUserNameChange(val name: String) : LoginEvents()
    data class OnPasswordChange(val password: String) : LoginEvents()
    data class OnRememberUserChecker(val isCheck: Boolean) : LoginEvents()
    data class OnShowPassword(val show: Boolean) : LoginEvents()
    data object OnLoginClick : LoginEvents()
    data object OnForgetPasswordClick : LoginEvents()
    data object OnCloseDialogForgetPassword : LoginEvents()
    data object OnCloseErrorDialog : LoginEvents()
    data object GoToSignUpScreen : LoginEvents()
    data object OnSignUpWithGoogle : LoginEvents()
}

sealed class LoginSideEffects {
    data object NavigateToHomeScreen : LoginSideEffects()
    data object NavigateToSignUp : LoginSideEffects()
    data object OnCloseFlow : LoginSideEffects()
    data object NavigateToScheduleNav : LoginSideEffects()
}

data class LoginUiState(
    val userName: String = "",
    val password: String = "",
    val rememberUserName: Boolean = false,
    val isLoading: Boolean = false,
    val showPassword: Boolean = false,
    val showDialogForgetPassword: Boolean = false,
    val isButtonLoginEnable: Boolean = false,
    val showErrorPassword: Boolean = false,
    val showErrorUserName: Boolean = false,
    val showResetPasswordError: Boolean = false,
    val showDialogPasswordOrEmailWrong: Boolean = false,
)

@HiltViewModel
class LoginViewModel
@Inject constructor(
    private val repository: Repository,
    private val loginPreferences: LoginPreferences
) : ViewModel() {

    private val _loginGoogleStatus = MutableLiveData<SessionStatus>()
    val loginGoogleStatus: LiveData<SessionStatus> get() = _loginGoogleStatus

    private val _state: MutableStateFlow<LoginUiState> =
        MutableStateFlow(LoginUiState().copy(userName = getUserEmailFromPreferences()))
    val state = _state.asStateFlow()


    private val _effects = Channel<LoginSideEffects>()
    val effects = _effects.receiveAsFlow()


    private fun saveUserEmailToPreferences(userEmail: String) {
        loginPreferences.saveUserEmail(userEmail)
    }

    private fun getUserEmailFromPreferences(): String {
        return loginPreferences.getUserEmail() ?: ""
    }

    private fun saveUserSession() {
        loginPreferences.saveUserSession()
    }

    fun getUserSession(): Boolean {
        return loginPreferences.getUserSession()
    }

    fun onEvents(event: LoginEvents) {
        when (event) {

            is LoginEvents.OnUserNameChange -> {
                _state.update { it.copy(userName = event.name) }
                validateForm()
            }

            is LoginEvents.OnPasswordChange -> {
                _state.update { it.copy(password = event.password) }
                validateForm()
            }

            is LoginEvents.OnRememberUserChecker -> {
                _state.update { it.copy(rememberUserName = event.isCheck) }
            }

            is LoginEvents.OnForgetPasswordClick -> {
                _state.update { it.copy(showDialogForgetPassword = true) }
            }

            is LoginEvents.OnLoginClick -> {
                login(email = state.value.userName, password = state.value.password)
            }

            is LoginEvents.OnSignUpWithGoogle -> {
                firebaseAuthWithGoogle("343333")
            }

            is LoginEvents.GoToSignUpScreen -> {
                viewModelScope.launch {
                    _effects.send(LoginSideEffects.NavigateToSignUp)
                }
            }

            is LoginEvents.OnCloseDialogForgetPassword -> {
                _state.update { it.copy(showDialogForgetPassword = false) }
            }

            is LoginEvents.OnShowPassword -> {
                _state.update { it.copy(showPassword = event.show) }
            }

            LoginEvents.OnCloseErrorDialog -> {
                _state.update { it.copy(showDialogPasswordOrEmailWrong = false) }
            }
        }
    }

    private fun validateForm() {
        _state.update { it.copy(showErrorUserName = false, showErrorPassword = false) }
        if (!isValidEmail(state.value.userName)) {
            _state.update { it.copy(showErrorUserName = true) }
            return
        }
        if (!isValidPassword()) {
            _state.update { it.copy(showErrorPassword = true) }
        }
        _state.update { it.copy(isButtonLoginEnable = true) }
    }

    private fun isValidPassword(): Boolean {
        val passwordLength = state.value.password.trim().length
        return passwordLength > 8
    }

    fun login(email: String, password: String) = viewModelScope.launch {
        _state.update { oldState -> oldState.copy(isLoading = true) }
        delay(1.seconds)
        repository.login(email = email, password = password).addOnCompleteListener { response ->
            if (response.isSuccessful) {
                saveUserSession()
                saveUserEmailToPreferences(email)
                viewModelScope.launch {
                    _effects.send(LoginSideEffects.NavigateToHomeScreen)
                }
            } else {
                _state.update { oldState ->
                    oldState.copy(
                        isLoading = false,
                        showDialogPasswordOrEmailWrong = true
                    )
                }
            }
        }
    }

    fun isUserActive(): Boolean {
        return repository.getUser() != null
    }

    fun firebaseAuthWithGoogle(idToken: String) {
        _loginGoogleStatus.value = SessionStatus.LOADING
//        if (!networkHelper.isNetworkConnected()) {
//            _loginGoogleStatus.value = SessionStatus.NETWORKERROR
//            return
//        }
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        repository.signInWithCredential(credential).addOnCompleteListener {
            if (it.isSuccessful) {
                _loginGoogleStatus.value = SessionStatus.SUCCESS
            } else {
                _loginGoogleStatus.value = SessionStatus.ERROR

            }
        }
    }


}