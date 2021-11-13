package com.example.citassalon.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.citassalon.data.firebase.FirebaseRepository
import com.example.citassalon.util.SessionStatus
import com.example.citassalon.util.NetworkHelper
import com.example.citassalon.util.PreferencesManager
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

    fun saveUserEmailToPreferences(userEmail: String) {
        preferencesManager.saveUserEmail(userEmail)
    }

    fun getUserEmailFromPreferences(): String? {
        return preferencesManager.getUserEmail()
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


}