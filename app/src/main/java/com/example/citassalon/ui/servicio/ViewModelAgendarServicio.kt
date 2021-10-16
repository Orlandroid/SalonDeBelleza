package com.example.citassalon.ui.servicio


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.citassalon.data.models.Servicio
import com.example.citassalon.util.ApiState
import com.example.citassalon.util.NetworkHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewModelAgendarServicio @Inject constructor(
    private val serviceRepository: ServiceRepository,
    private val networkHelper: NetworkHelper
) :
    ViewModel() {

    private val _services = MutableLiveData<ApiState<List<Servicio>>>()
    val services: MutableLiveData<ApiState<List<Servicio>>>
        get() = _services

    init {
        getServices()
    }

    fun getServices() {
        viewModelScope.launch(Dispatchers.IO) {
            _services.postValue(ApiState.Loading(null))
            if (networkHelper.isNetworkConnected()) {
                val response = serviceRepository.getServices()
                if (response.isSuccessful) {
                    _services.postValue(ApiState.Success(response.body()!!))
                }
            } else {
                _services.postValue(ApiState.ErrorNetwork())
            }
        }
    }


}