package com.example.citassalon.ui.sign_up

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.citassalon.data.firebase.FirebaseRepository
import com.example.citassalon.data.models.User
import com.example.citassalon.util.NetworkHelper
import com.example.citassalon.util.SessionStatus
import com.google.firebase.database.FirebaseDatabase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ViewModelSignUp @Inject constructor(
    private val firebaseRepository: FirebaseRepository,
    private val firebaseDatabase: FirebaseDatabase,
    private val networkHelper: NetworkHelper
) : ViewModel() {

    private val _singUp = MutableLiveData<SessionStatus>()
    val singUp: LiveData<SessionStatus> get() = _singUp

    fun sinUp(email: String, password: String) {
        _singUp.value = SessionStatus.LOADING
        if (networkHelper.isNetworkConnected()) {
            firebaseRepository.registrer(email, password).addOnCompleteListener {
                if (it.isSuccessful) {
                    _singUp.value = SessionStatus.SUCESS
                } else {
                    _singUp.value = SessionStatus.ERROR
                }
            }
        } else {
            _singUp.value = SessionStatus.NETWORKERROR
        }
    }

    fun saveUserImformation(user: User) {
        if (!networkHelper.isNetworkConnected()) {
            return
        }
        val userUii = firebaseRepository.getUser()?.uid ?: return
        Log.w("Usuario",userUii)
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