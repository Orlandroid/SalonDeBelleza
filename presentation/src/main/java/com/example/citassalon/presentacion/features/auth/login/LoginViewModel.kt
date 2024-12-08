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
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
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

    private fun saveUserEmailToPreferences(userEmail: String) {
        loginPreferences.saveUserEmail(userEmail)
    }

    fun getUserEmailFromPreferences(): String? {
        return loginPreferences.getUserEmail()
    }

    private fun saveUserSession() {
        loginPreferences.saveUserSession()
    }

    fun getUserSession(): Boolean {
        return loginPreferences.getUserSession()
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

    private val _state: MutableStateFlow<BaseScreenState<Unit>> =
        MutableStateFlow(BaseScreenState.Idle())
    val state = _state.asStateFlow()

    fun loginV2(email: String, password: String) = viewModelScope.launch {
        _state.value = BaseScreenState.Loading()
        delay(1.seconds)
        if (!networkHelper.isNetworkConnected()) {
            _state.value = BaseScreenState.ErrorNetwork()
        }
        repository.login(email = email, password = password).addOnCompleteListener { response ->
            if (response.isSuccessful) {
                _state.value = BaseScreenState.Success(Unit)
                saveUserSession()
                saveUserEmailToPreferences(email)
            } else {
                _state.value = BaseScreenState.Error(Exception(response.exception?.message))
            }
        }
    }


//    fun login(email: String, password: String) = viewModelScope.launch {
//        _status.update {
//            it.copy(isLoading = true)
//        }
//        delay(2.seconds)
//        if (email.isEmpty() or password.isEmpty()) {
//            _status.update {
//                it.copy(isEmptyFields = true, isLoading = false)
//            }
//            return@launch
//        }
//        if (!networkHelper.isNetworkConnected()) {
//            _status.update {
//                it.copy(isError = true)
//            }
//        }
//        repository.login(email = email, password = password).addOnCompleteListener { task ->
//            if (task.isSuccessful) {
//                _effects.value = LoginUiEffect.NavigateToHomeScreen
//                _status.update { it.copy(isLoading = false) }
//                saveUserSession()
//                saveUserEmailToPreferences(email)
//            } else {
//                _status.update {
//                    it.copy(isError = true, isLoading = false)
//                }
//            }
//        }
//    }

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