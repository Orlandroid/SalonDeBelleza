package com.example.citassalon.presentacion.features.login


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.citassalon.presentacion.main.NetworkHelper
import com.example.data.Repository
import com.example.data.preferences.LoginPreferences
import com.example.domain.state.SessionStatus
import com.google.firebase.auth.GoogleAuthProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel
@Inject constructor(
    private val networkHelper: NetworkHelper,
    private val repository: Repository,
    private val loginPeferences: LoginPreferences,
) : ViewModel() {

    private val _status: MutableStateFlow<LoginUiState> = MutableStateFlow(LoginUiState())
    val status = _status.asStateFlow()

    fun handleActions(actions: LoginActions) {
        when (actions) {
            is LoginActions.ForgetPassword -> {

            }

            is LoginActions.Login -> {
                login(actions.email, actions.password)
            }

            is LoginUiEffect.NavigateToHomeScreen -> {

            }

            is LoginActions.RememberUser -> {

            }

            is LoginActions.SignUp -> {

            }

            is LoginActions.SignUpWithGoogle -> {

            }
        }
    }

    fun handleEffect(effect: LoginUiEffect) {

    }

    private val _forgetPasswordStatus = MutableLiveData<SessionStatus>()
    val forgetPasswordStatus: LiveData<SessionStatus> get() = _forgetPasswordStatus

    private val _loginGoogleStatus = MutableLiveData<SessionStatus>()
    val loginGoogleStatus: LiveData<SessionStatus> get() = _loginGoogleStatus

    fun saveUserEmailToPreferences(userEmail: String) {
        loginPeferences.saveUserEmail(userEmail)
    }

    fun getUserEmailFromPreferences(): String? {
        return loginPeferences.getUserEmail()
    }

    private fun saveUserSession() {
        loginPeferences.saveUserSession()
    }

    fun getUserSession(): Boolean {
        return loginPeferences.getUserSession()
    }


    fun forgetPassword(email: String) {
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
        if (email.isEmpty() or password.isEmpty()) {
            //android
        }
        _status.update {
            it.copy(isLoading = true)
        }
        if (!networkHelper.isNetworkConnected()) {
            _status.update {
                it.copy(isError = true)
            }
        }
        repository.login(email = email, password = password).addOnCompleteListener {
            if (it.isSuccessful) {

            } else {

            }
        }
    }

//    fun login(email: String, password: String) = viewModelScope.launch {
//        _status.emit(SessionStatus.LOADING)
//        if (networkHelper.isNetworkConnected()) {
//            repository.login(email, password).addOnCompleteListener {
//                if (it.isSuccessful) {
//                    viewModelScope.launch {
//                        _status.emit(SessionStatus.SUCCESS)
//                    }
//                    saveUserSession()
//                } else {
//                    viewModelScope.launch {
//                        _status.emit(SessionStatus.ERROR)
//                    }
//                }
//            }
//        } else {
//            _status.emit(SessionStatus.NETWORKERROR)
//        }
//    }

//    fun loginUi(user: String, password: String, onEmptyFields: () -> Unit) {
//        if (user.isEmpty() || password.isEmpty()) {
//            onEmptyFields.invoke()
//            return
//        }
//        saveUserEmailToPreferences(user)
//        login(user, password)
//    }

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