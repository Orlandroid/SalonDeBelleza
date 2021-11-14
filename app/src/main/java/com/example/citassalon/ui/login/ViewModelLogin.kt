package com.example.citassalon.ui.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.citassalon.data.firebase.FirebaseRepository
import com.example.citassalon.util.SessionStatus
import com.example.citassalon.util.NetworkHelper
import com.example.citassalon.util.PreferencesManager
import com.google.firebase.auth.GoogleAuthProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ViewModelLogin
@Inject constructor(
    private val networkHelper: NetworkHelper,
    private val firebaseRepository: FirebaseRepository,
    private val preferencesManager: PreferencesManager
) : ViewModel() {

    private val _loginStatus = MutableLiveData<SessionStatus>()
    val loginStatus: LiveData<SessionStatus> get() = _loginStatus

    private val _forgetPasswordStatus = MutableLiveData<SessionStatus>()
    val forgetPasswordStatus: LiveData<SessionStatus> get() = _forgetPasswordStatus

    private val _loginGoogleStatus = MutableLiveData<SessionStatus>()
    val loginGoogleStatus: LiveData<SessionStatus> get() = _loginGoogleStatus

    fun saveUserEmailToPreferences(userEmail: String) {
        preferencesManager.saveUserEmail(userEmail)
    }

    fun getUserEmailFromPreferences(): String? {
        return preferencesManager.getUserEmail()
    }

    fun forgetPassword(email: String) {
        _forgetPasswordStatus.value = SessionStatus.LOADING
        if (!networkHelper.isNetworkConnected()) {
            _forgetPasswordStatus.value = SessionStatus.NETWORKERROR
            return
        }
        firebaseRepository.forgetPassword(email).addOnCompleteListener {
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
            firebaseRepository.login(email, password)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        _loginStatus.value = SessionStatus.SUCESS
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
        firebaseRepository.signInWithCredential(credential).addOnCompleteListener {
            if (it.isSuccessful) {
                _loginGoogleStatus.value = SessionStatus.SUCESS
            } else {
                _loginGoogleStatus.value = SessionStatus.ERROR

            }
        }
    }


}