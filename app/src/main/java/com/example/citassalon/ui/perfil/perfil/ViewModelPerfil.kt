package com.example.citassalon.ui.perfil.perfil

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.citassalon.data.preferences.LoginPeferences
import com.example.citassalon.data.repository.Repository
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ViewModelPerfil @Inject constructor(
    private val repository: Repository,
    private val loginPeferences: LoginPeferences
) :
    ViewModel() {

    private val _firebaseUser = MutableLiveData<FirebaseUser>()
    val firebaseUser: LiveData<FirebaseUser> get() = _firebaseUser

    fun destroyUserSession() {
        loginPeferences.destroyUserSession()
    }

    fun logout() {
        repository.logout()
    }

    init {
        _firebaseUser.value = repository.getUser()
    }

}