package com.example.citassalon.ui.perfil

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.citassalon.data.firebase.FirebaseRepository
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ViewModelPerfil @Inject constructor(private val firebaseRepository: FirebaseRepository) :
    ViewModel() {

    private val _firebaseUser = MutableLiveData<FirebaseUser>()
    val firebaseUser: LiveData<FirebaseUser> get() = _firebaseUser

    init {
        _firebaseUser.value = firebaseRepository.getUser()
    }

}