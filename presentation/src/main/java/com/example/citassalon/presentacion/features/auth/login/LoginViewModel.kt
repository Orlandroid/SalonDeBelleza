package com.example.citassalon.presentacion.features.auth.login


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.citassalon.presentacion.features.base.BaseViewModel
import com.example.citassalon.presentacion.features.schedule_appointment.branches.BaseScreenState
import com.example.citassalon.presentacion.main.NetworkHelper
import com.example.data.Repository
import com.example.data.di.CoroutineDispatchers
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

@HiltViewModel
class LoginViewModel
@Inject constructor(
    networkHelper: NetworkHelper,
    private val repository: Repository,
    coroutineDispatchers: CoroutineDispatchers,
    private val loginPreferences: LoginPreferences,
) : BaseViewModel(
    coroutineDispatchers = coroutineDispatchers,
    networkHelper = networkHelper
) {
    private val _forgetPasswordStatus = MutableLiveData<SessionStatus>()
    val forgetPasswordStatus: LiveData<SessionStatus> get() = _forgetPasswordStatus

    private val _loginGoogleStatus = MutableLiveData<SessionStatus>()
    val loginGoogleStatus: LiveData<SessionStatus> get() = _loginGoogleStatus

    private val _uiState: MutableStateFlow<LoginUiState> =
        MutableStateFlow(LoginUiState().copy(userName = getUserEmailFromPreferences()))
    val uiState = _uiState.asStateFlow()

    private val _state: MutableStateFlow<BaseScreenState<Unit>> =
        MutableStateFlow(BaseScreenState.Idle())
    val state = _state.asStateFlow()

    private val _loginSideEffects = Channel<LoginSideEffects>()
    val loginSideEffects = _loginSideEffects.receiveAsFlow()


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
                _uiState.update { oldState -> oldState.copy(userName = event.name) }
                validateForm()
            }

            is LoginEvents.OnPasswordChange -> {
                _uiState.update { oldState -> oldState.copy(password = event.password) }
                validateForm()
            }

            is LoginEvents.OnRememberUserChecker -> {
                _uiState.update { oldState -> oldState.copy(rememberUserName = event.isCheck) }
            }

            is LoginEvents.OnForgetPasswordClick -> {
                _uiState.update { oldState -> oldState.copy(showDialogForgetPassword = true) }
            }

            is LoginEvents.OnLoginClick -> {
                login(
                    email = uiState.value.userName,
                    password = uiState.value.password
                )
            }

            is LoginEvents.OnSignUpWithGoogle -> {
//                firebaseAuthWithGoogle()
            }

            is LoginEvents.GoToSignUpScreen -> {
                viewModelScope.launch {
                    _loginSideEffects.send(LoginSideEffects.GoToSignUp)
                }
            }

            is LoginEvents.OnCloseDialogForgetPassword -> {
                _uiState.update { oldState -> oldState.copy(showDialogForgetPassword = false) }
            }

            is LoginEvents.OnResetPassword -> {
                forgetPassword(event.email)
            }

            is LoginEvents.OnShowPassword -> {
                _uiState.update { oldState -> oldState.copy(showPassword = event.show) }
            }

            LoginEvents.OnSuccessLogin -> {
                viewModelScope.launch {
                    _loginSideEffects.send(LoginSideEffects.NavigateToHomeScreen)
                }
            }
        }
    }

    private fun validateForm() {
        if (uiState.value.userName.isEmpty()) {
            _uiState.update { oldState -> oldState.copy(showErrorUserName = true) }
            //Todo Add one error label in the login screen to say to the user that user name can,t be empty
            return
        }
        if (uiState.value.password.isEmpty()) {
            _uiState.update { oldState -> oldState.copy(showErrorPassword = true) }
            //Todo Add one error label in the login screen to say to the user that password can,t be empty
            return
        }
        _uiState.update { oldState -> oldState.copy(isButtonLoginEnable = true) }
    }

    private fun forgetPassword(email: String) {
        _forgetPasswordStatus.value = SessionStatus.LOADING
        if (!networkHelper.isNetworkConnected()) {
            _forgetPasswordStatus.value = SessionStatus.NETWORKERROR
            return
        }
        repository.forgetPassword(email).addOnCompleteListener {
            if (it.isSuccessful) {
                _forgetPasswordStatus.value = SessionStatus.SUCCESS
            } else {
                _forgetPasswordStatus.value = SessionStatus.ERROR
            }
        }
    }

    fun login(email: String, password: String) = viewModelScope.launch {
        _uiState.update { oldState -> oldState.copy(isLoading = true) }
        delay(1.seconds)
        if (!networkHelper.isNetworkConnected()) {
            _state.value = BaseScreenState.ErrorNetwork()
            _uiState.update { oldState -> oldState.copy(isLoading = false) }
        }
        repository.login(email = email, password = password).addOnCompleteListener { response ->
            if (response.isSuccessful) {
                saveUserSession()
                saveUserEmailToPreferences(email)
                viewModelScope.launch {
                    _loginSideEffects.send(LoginSideEffects.NavigateToHomeScreen)
                }
            } else {
                _state.value = BaseScreenState.Error(Exception(response.exception?.message))
                _uiState.update { oldState -> oldState.copy(isLoading = false) }
            }
        }
    }

    fun isUserActive(): Boolean {
        return repository.getUser() != null
    }

    fun firebaseAuthWithGoogle(idToken: String) {
        _loginGoogleStatus.value = SessionStatus.LOADING
        if (!networkHelper.isNetworkConnected()) {
            _loginGoogleStatus.value = SessionStatus.NETWORKERROR
            return
        }
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