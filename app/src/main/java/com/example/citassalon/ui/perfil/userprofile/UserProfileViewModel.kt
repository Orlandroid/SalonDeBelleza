package com.example.citassalon.ui.perfil.userprofile


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.citassalon.data.repository.Repository
import com.example.citassalon.data.state.ApiState
import com.example.citassalon.main.NetworkHelper
import com.example.citassalon.ui.perfil.userprofile.UserProfileFragment.Companion.USER_EMAIL
import com.example.citassalon.ui.perfil.userprofile.UserProfileFragment.Companion.USER_UID
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class UserProfileViewModel @Inject constructor(
    private val networkHelper: NetworkHelper,
    private val repository: Repository
) : ViewModel() {

    private val _infoUser = MutableLiveData<ApiState<HashMap<String, String>>>()
    val infoUser: LiveData<ApiState<HashMap<String, String>>> get() = _infoUser

    fun getUserInfo() {
        viewModelScope.launch(Dispatchers.IO) {
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
                withContext(Dispatchers.Main) {
                    _infoUser.value = ApiState.Success(userInfo)
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    _infoUser.value = ApiState.Error(e)
                }
            }
        }

    }


}