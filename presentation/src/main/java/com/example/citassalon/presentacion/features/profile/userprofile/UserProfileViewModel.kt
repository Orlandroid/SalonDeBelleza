package com.example.citassalon.presentacion.features.profile.userprofile


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.citassalon.presentacion.features.base.BaseViewModel
import com.example.citassalon.presentacion.features.base.BaseScreenState
import com.example.citassalon.presentacion.main.NetworkHelper
import com.example.data.di.CoroutineDispatchers
import com.example.data.preferences.LoginPreferences
import com.example.domain.perfil.RandomUserResponse
import com.example.domain.perfil.UserInfo
import com.example.domain.perfil.UserProfileResponse
import com.example.domain.state.ApiState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
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

    private val _infoUserState: MutableStateFlow<BaseScreenState<UserProfileResponse>> =
        MutableStateFlow(BaseScreenState.Idle())
    val infoUserState = _infoUserState.asStateFlow()

    private val _remoteImageProfileState: MutableStateFlow<BaseScreenState<String>> =
        MutableStateFlow(BaseScreenState.Idle())
    val remoteImageProfileState = _remoteImageProfileState.asStateFlow()

    private val _localImageState: MutableStateFlow<BaseScreenState<String>> =
        MutableStateFlow(BaseScreenState.Idle())
    val localImageState = _localImageState.asStateFlow()


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

    private fun getUserMoney() = loginPreferences.getUserMoney().toString()


    private fun provideFirebaseRealtimeDatabaseReference(
        firebaseDatabase: FirebaseDatabase, firebaseAuth: FirebaseAuth
    ): DatabaseReference {
        val uuidUser = firebaseAuth.uid
        return firebaseDatabase.reference.child(IMAGE_USER).child(uuidUser!!)
    }


    fun getUserInfo() = viewModelScope.launch(Dispatchers.IO) {
        withContext(Dispatchers.Main) {
            _infoUserState.value = BaseScreenState.Loading()
        }
        if (!networkHelper.isNetworkConnected()) {
            withContext(Dispatchers.Main) {
                _infoUserState.value = BaseScreenState.ErrorNetwork()
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
                val listUserInfo = arrayListOf<UserInfo>()
                listUserInfo.add(UserInfo("Nombre", value = user.displayName ?: ""))
                listUserInfo.add(UserInfo("Telefono"))
                listUserInfo.add(UserInfo("correo", user.email ?: ""))
                listUserInfo.add(UserInfo("uid", user.uid))
                listUserInfo.add(
                    UserInfo(
                        "Money", "$ ${getUserMoney()}"
                    )
                )
                _infoUserState.value = BaseScreenState.Success(
                    UserProfileResponse(
                        userInfo = listUserInfo,
                        isUserSessionActive = repository.getUser() != null
                    )
                )
            }
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                _infoUserState.value = BaseScreenState.Error(e)
            }
        }
    }


    fun getUserImage() = viewModelScope.launch {
        _remoteImageProfileState.value = BaseScreenState.Loading()
        if (!networkHelper.isNetworkConnected()) {
            withContext(Dispatchers.Main) {
                _remoteImageProfileState.value = BaseScreenState.ErrorNetwork()
            }
            return@launch
        }
        val databaseReference =
            provideFirebaseRealtimeDatabaseReference(firebaseDatabase, firebaseAuth)
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.value.toString() != "null") {
                    val imageUser = snapshot.value.toString()
                    _remoteImageProfileState.value = BaseScreenState.Success(imageUser)
                } else {
                    _remoteImageProfileState.value =
                        BaseScreenState.Error(exception = Exception("error"))
                }
            }

            override fun onCancelled(error: DatabaseError) {
                _remoteImageProfileState.value =
                    BaseScreenState.Error(exception = Exception(error.message))
            }
        })
    }


    fun saveImageUser(imageLikeBase64: String) = viewModelScope.launch(Dispatchers.IO) {
        withContext(Dispatchers.Main) {
            _localImageState.value = BaseScreenState.Loading()
        }
        if (!networkHelper.isNetworkConnected()) {
            withContext(Dispatchers.Main) {
                _localImageState.value = BaseScreenState.ErrorNetwork()
            }
            return@launch
        }
        val userUii = repository.getUser()?.uid ?: return@launch
        firebaseDatabase.getReference(IMAGE_USER).child(userUii).setValue(imageLikeBase64)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    _localImageState.value = BaseScreenState.Success(it.isSuccessful.toString())
                } else {
                    _localImageState.value = BaseScreenState.Error(exception = Exception())
                }
            }
    }


}