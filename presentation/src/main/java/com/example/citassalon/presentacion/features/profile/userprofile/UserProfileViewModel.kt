package com.example.citassalon.presentacion.features.profile.userprofile


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.citassalon.presentacion.main.NetworkHelper
import com.example.citassalon.presentacion.features.base.BaseViewModel
import com.example.data.di.CoroutineDispatchers
import com.example.data.preferences.LoginPreferences
import com.example.domain.perfil.RandomUserResponse
import com.example.domain.state.ApiState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class UserProfileViewModel @Inject constructor(
    coroutineDispatchers: CoroutineDispatchers,
    networkHelper: NetworkHelper,
    private val repository: com.example.data.Repository,
    private val firebaseDatabase: FirebaseDatabase,
    private val firebaseAuth: FirebaseAuth,
    private val loginPreferences: LoginPreferences
) : BaseViewModel(coroutineDispatchers, networkHelper) {

    private val _infoUser = MutableLiveData<ApiState<HashMap<String, String>>>()
    val infoUser: LiveData<ApiState<HashMap<String, String>>> get() = _infoUser

    private val _imageUser = MutableLiveData<ApiState<String>>()
    val imageUser: LiveData<ApiState<String>> get() = _imageUser

    private val _imageUserProfile = MutableLiveData<ApiState<String?>>()
    val imageUserProfile: LiveData<ApiState<String?>> get() = _imageUserProfile


    private val _randomUserResponse = MutableLiveData<ApiState<RandomUserResponse>>()
    val randomUserResponse: LiveData<ApiState<RandomUserResponse>> get() = _randomUserResponse

    companion object {
        private const val IMAGE_USER = "imageUser"
    }

    fun randomUser() = viewModelScope.launch {
        safeApiCall(_randomUserResponse, coroutineDispatchers) {
            val response = repository.randomUser()
            withContext(Dispatchers.Main) {
                _randomUserResponse.value = ApiState.Success(response)
            }
        }
    }

    fun getUserMoney() = loginPreferences.getUserMoney().toString()


    private fun provideFirebaseRealtimeDatabaseReference(
        firebaseDatabase: FirebaseDatabase, firebaseAuth: FirebaseAuth
    ): DatabaseReference {
        val uuidUser = firebaseAuth.uid
        return firebaseDatabase.reference.child(IMAGE_USER).child(uuidUser!!)
    }

    fun getUserInfo() = viewModelScope.launch(Dispatchers.IO) {
        withContext(Dispatchers.Main) {
            _infoUser.value = ApiState.Loading()
        }
        delay(2000)
        if (!networkHelper.isNetworkConnected()) {
            withContext(Dispatchers.Main) {
                _infoUser.value = ApiState.ErrorNetwork()
            }
            return@launch
        }
        try {
            val user = repository.getUser() ?: return@launch
            val userInfo = HashMap<String, String>()
            userInfo[USER_EMAIL] = user.email!!
            userInfo[USER_UID] = user.uid
            userInfo[USER_SESSION] = (repository.getUser() != null).toString()
            withContext(Dispatchers.Main) {
                _infoUser.value = ApiState.Success(userInfo)
            }
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                _infoUser.value = ApiState.Error(e)
            }
        }

    }

    fun getUserImage() = viewModelScope.launch {
        _imageUserProfile.value = ApiState.Loading()
        if (!networkHelper.isNetworkConnected()) {
            withContext(Dispatchers.Main) {
                _imageUserProfile.value = ApiState.ErrorNetwork()
            }
            return@launch
        }
        val databaseReference =
            provideFirebaseRealtimeDatabaseReference(firebaseDatabase, firebaseAuth)
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val imageUser = snapshot.value.toString()
                _imageUserProfile.value = ApiState.Success(imageUser)
            }

            override fun onCancelled(error: DatabaseError) {
                _imageUserProfile.value = ApiState.Error(Throwable(message = error.message))
            }
        })

    }

    fun saveImageUser(imageLikeBase64: String) = viewModelScope.launch(Dispatchers.IO) {
        withContext(Dispatchers.Main) {
            _imageUser.value = ApiState.Loading()
        }
        if (!networkHelper.isNetworkConnected()) {
            withContext(Dispatchers.Main) {
                _imageUser.value = ApiState.ErrorNetwork()
            }
            return@launch
        }
        val userUii = repository.getUser()?.uid ?: return@launch
        firebaseDatabase.getReference(IMAGE_USER).child(userUii).setValue(imageLikeBase64)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    _imageUser.value = ApiState.Success(it.isSuccessful.toString())
                } else {
                    _imageUser.value = ApiState.Error(Throwable(message = "error saving image"))
                }
            }
    }


}