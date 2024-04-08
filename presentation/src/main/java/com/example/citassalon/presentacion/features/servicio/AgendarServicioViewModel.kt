package com.example.citassalon.presentacion.features.servicio


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.citassalon.presentacion.main.NetworkHelper
import com.example.domain.entities.remote.Servicio
import com.example.domain.state.ApiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AgendarServicioViewModel @Inject constructor(
    private val repository: com.example.data.Repository,
    private val networkHelper: NetworkHelper
) :
    ViewModel() {

    private val _services = MutableLiveData<ApiState<List<Servicio>>>()
    val services: MutableLiveData<ApiState<List<Servicio>>>
        get() = _services

    init {
        getServices()
    }

    private fun getServices() {
        viewModelScope.launch(Dispatchers.IO) {
            _services.postValue(ApiState.Loading())
            if (!networkHelper.isNetworkConnected()) {
                _services.postValue(ApiState.ErrorNetwork())
            }
            try {
                val response = repository.getServices()
                if (response.isEmpty()) {
                    _services.postValue(ApiState.NoData())
                }
                _services.postValue(ApiState.Success(response))
            } catch (e: Exception) {
                _services.postValue(ApiState.Error(e))
            }
        }
    }


}