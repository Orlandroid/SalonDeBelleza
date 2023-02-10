package com.example.citassalon.presentacion.ui.info.nuestro_staff


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.citassalon.data.Repository
import com.example.citassalon.data.di.CoroutineDispatchers
import com.example.citassalon.domain.entities.remote.dummyUsers.DummyUsersResponse
import com.example.citassalon.domain.state.ApiState
import com.example.citassalon.presentacion.main.NetworkHelper
import com.example.citassalon.presentacion.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class OurStaffViewModel @Inject constructor(
    coroutineDispatchers: CoroutineDispatchers,
    networkHelper: NetworkHelper,
    private val repository: Repository
) : BaseViewModel(coroutineDispatchers, networkHelper) {


    private val _ourStaffsResponse = MutableLiveData<ApiState<DummyUsersResponse>>()
    val ourStaffsResponse: LiveData<ApiState<DummyUsersResponse>> get() = _ourStaffsResponse


    fun getStaffsUsers() {
        viewModelScope.launch {
            safeApiCall(_ourStaffsResponse, coroutineDispatchers) {
                val response = repository.getStaffUsers()
                withContext(Dispatchers.Main) {
                    _ourStaffsResponse.value = ApiState.Success(response)
                }
            }
        }
    }


}