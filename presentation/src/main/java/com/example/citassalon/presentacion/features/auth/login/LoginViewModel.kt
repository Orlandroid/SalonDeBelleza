package com.example.citassalon.presentacion.features.auth.login


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.validation.EmailValidator
import com.example.domain.validation.PasswordValidator
import com.example.data.di.IoDispatcher
import com.example.data.preferences.LoginPreferences
import com.example.data.remote.auth.AuthRepository
import com.example.domain.state.getResultOrNull
import com.example.domain.state.isError
import com.example.domain.state.isSuccess
import com.google.firebase.auth.GoogleAuthProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


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
    val showDialogPasswordOrEmailWrong: Boolean = false
)

@HiltViewModel
class LoginViewModel
@Inject constructor(
    private val authRepository: AuthRepository,
    private val loginPreferences: LoginPreferences,
    private val emailValidator: EmailValidator,
    private val passwordValidator: PasswordValidator,
    @param:IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _state: MutableStateFlow<LoginUiState> =
        MutableStateFlow(LoginUiState().copy(userName = "getUserEmailFromPreferences()"))
    val state = _state.onStart {
        val userEmail = getUserEmailFromPreferences().await()
        _state.update { it.copy(userName = userEmail) }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = LoginUiState()
    )


    private val _effects = Channel<LoginSideEffects>()
    val effects = _effects.receiveAsFlow()

    val isUserLoginStatus = false


    private fun saveUserEmailToPreferences(userEmail: String) {
        viewModelScope.launch {
            loginPreferences.saveUserEmail(userEmail)
        }
    }

    private fun getUserEmailFromPreferences(): Deferred<String> {
        return viewModelScope.async { loginPreferences.getUserEmail() ?: "" }
    }

    private fun saveUserSession() {
        viewModelScope.launch {
            loginPreferences.saveUserLogged()
        }
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
                firebaseAuthWithGoogle("619340747074-93lsb31bhcsp1nkptvkve9rlhecbclnd.apps.googleusercontent.com")
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
        if (!emailValidator.isValidEmail(state.value.userName)) {
            _state.update { it.copy(showErrorUserName = true) }
            return
        }
        if (!passwordValidator.isValidPassword(state.value.password)) {
            _state.update { it.copy(showErrorPassword = true) }
        }
        _state.update { it.copy(isButtonLoginEnable = true) }
    }

    fun login(
        email: String,
        password: String
    ) = viewModelScope.launch {
        _state.update { oldState -> oldState.copy(isLoading = true) }
        val loginResult = authRepository.login(email = email, password = password)

        if (loginResult.isSuccess()) {
            saveUserSession()
            saveUserEmailToPreferences(email)
            _effects.send(LoginSideEffects.NavigateToHomeScreen)
        } else {
            _state.update { oldState ->
                oldState.copy(
                    isLoading = false,
                    showDialogPasswordOrEmailWrong = true
                )
            }
        }
    }

    fun isUserActive(): Boolean {
        val userResult = authRepository.getUser()
        if (userResult.isSuccess()) {
            return userResult.getResultOrNull() != null
        }
        return false
    }

    fun firebaseAuthWithGoogle(idToken: String) {
        viewModelScope.launch(ioDispatcher) {
            val credential = GoogleAuthProvider.getCredential(
                idToken,
                null
            )
            val authResult = authRepository.signInWithCredential(credential)
            if (authResult.isError()) {
                print("Error al iniciar sesión con Google")
                return@launch
            }
            print("Sesión iniciada con Google")
        }
    }


}