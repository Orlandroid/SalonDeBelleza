package com.example.citassalon.ui.servicio


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.citassalon.data.models.Servicio
import com.example.citassalon.data.repository.Repository
import com.example.citassalon.data.state.ApiState
import com.example.citassalon.main.NetworkHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewModelAgendarServicio @Inject constructor(
    private val repository: Repository,
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