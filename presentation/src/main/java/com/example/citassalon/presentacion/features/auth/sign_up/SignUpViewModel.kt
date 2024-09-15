package com.example.citassalon.presentacion.features.auth.sign_up

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.citassalon.presentacion.main.NetworkHelper
import com.example.domain.entities.remote.User
import com.example.domain.state.SessionStatus
import com.google.firebase.database.FirebaseDatabase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val repository: com.example.data.Repository,
    private val firebaseDatabase: FirebaseDatabase,
    private val networkHelper: NetworkHelper
) : ViewModel() {

    private val _singUp = MutableLiveData<SessionStatus>()
    val singUp: LiveData<SessionStatus> get() = _singUp

    var errorPhone = mutableStateOf(false)
    var errorEmail = mutableStateOf(false)
    var errorPassword = mutableStateOf(false)

    fun sinUp(email: String, password: String) {
        _singUp.value = SessionStatus.LOADING
        if (networkHelper.isNetworkConnected()) {
            repository.register(email, password).addOnCompleteListener {
                if (it.isSuccessful) {
                    _singUp.value = SessionStatus.SUCCESS
                } else {
                    _singUp.value = SessionStatus.ERROR
                }
            }
        } else {
            _singUp.value = SessionStatus.NETWORKERROR
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

}