package com.example.citassalon.ui.perfil

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.citassalon.data.firebase.FirebaseRepository
import com.example.citassalon.data.preferences.LoginPeferences
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ViewModelPerfil @Inject constructor(
    private val firebaseRepository: FirebaseRepository,
    private val loginPeferences: LoginPeferences
) :
    ViewModel() {

    private val _firebaseUser = MutableLiveData<FirebaseUser>()
    val firebaseUser: LiveData<FirebaseUser> get() = _firebaseUser

    fun destroyUserSession() {
        loginPeferences.destroyUserSession()
    }

    init {
        _firebaseUser.value = firebaseRepository.getUser()
    }

}