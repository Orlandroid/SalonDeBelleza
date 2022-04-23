package com.example.citassalon.ui.login


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.citassalon.data.preferences.LoginPeferences
import com.example.citassalon.data.repository.Repository
import com.example.citassalon.data.state.SessionStatus
import com.example.citassalon.main.NetworkHelper
import com.google.firebase.auth.GoogleAuthProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ViewModelLogin
@Inject constructor(
    private val networkHelper: NetworkHelper,
    private val repository: Repository,
    private val loginPeferences: LoginPeferences,
) : ViewModel() {

    private val _loginStatus = MutableLiveData<SessionStatus>()
    val loginStatus: LiveData<SessionStatus> get() = _loginStatus

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
                _forgetPasswordStatus.value = SessionStatus.SUCESS
            } else {
                _forgetPasswordStatus.value = SessionStatus.ERROR
            }
        }
    }

    fun login(email: String, password: String) {
        _loginStatus.value = SessionStatus.LOADING
        if (networkHelper.isNetworkConnected()) {
            repository.login(email, password)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        _loginStatus.value = SessionStatus.SUCESS
                        saveUserSession()
                    } else {
                        _loginStatus.value = SessionStatus.ERROR
                    }
                }
        } else {
            _loginStatus.value = SessionStatus.NETWORKERROR
        }
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
                _loginGoogleStatus.value = SessionStatus.SUCESS
            } else {
                _loginGoogleStatus.value = SessionStatus.ERROR

            }
        }
    }


}