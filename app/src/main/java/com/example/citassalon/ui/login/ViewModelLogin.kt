package com.example.citassalon.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.citassalon.util.LoginStatus
import com.example.citassalon.util.NetworkHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ViewModelLogin
@Inject constructor(
    private val networkHelper: NetworkHelper,
    private val firebaseRepository: FirebaseRepository
) : ViewModel() {

    private val _loginStatus = MutableLiveData<LoginStatus>()
    val loginStatus: LiveData<LoginStatus> get() = _loginStatus

    fun login(email: String, password: String) {
        _loginStatus.value = LoginStatus.LOADING
        if (networkHelper.isNetworkConnected()) {
            firebaseRepository.login(email, password)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        _loginStatus.value = LoginStatus.SUCESS
                    } else {
                        _loginStatus.value = LoginStatus.ERROR
                    }
                }
        } else {
            _loginStatus.value = LoginStatus.NETWORKERROR
        }
    }


}