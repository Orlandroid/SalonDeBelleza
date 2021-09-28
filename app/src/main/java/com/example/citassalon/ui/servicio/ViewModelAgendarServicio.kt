package com.example.citassalon.ui.servicio

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.citassalon.data.models.Servicio
import com.example.citassalon.data.repository.ServiceRepository
import com.example.citassalon.util.ApiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class ViewModelAgendarServicio @Inject constructor(private val serviceRepository: ServiceRepository) :
    ViewModel() {

    private val _servicesLivedata = MutableLiveData<ApiState<List<Servicio>>>()
    val serviceLiveData: MutableLiveData<ApiState<List<Servicio>>>
        get() = _servicesLivedata

    init {
        getSucursales()
    }

    private fun getSucursales() {
        viewModelScope.launch(Dispatchers.IO) {
            val serviceServices = serviceRepository.getServices()
            _servicesLivedata.postValue(ApiState.Loading(null))
            serviceServices.enqueue(object : Callback<List<Servicio>> {
                override fun onFailure(call: Call<List<Servicio>>, t: Throwable) {
                    _servicesLivedata.postValue(ApiState.Error(t, null))
                    Log.v("DEBUG : ", t.message.toString())
                }

                override fun onResponse(
                    call: Call<List<Servicio>>,
                    response: Response<List<Servicio>>
                ) {
                    if (response.isSuccessful) {
                        _servicesLivedata.postValue(ApiState.Success(response.body()!!))
                        Log.v("DEBUG : ", response.body().toString())
                    }
                }
            })
        }
    }

    fun cancelService() {
        viewModelScope.launch(Dispatchers.IO) {
            serviceRepository.getServices().cancel()
        }
    }
}