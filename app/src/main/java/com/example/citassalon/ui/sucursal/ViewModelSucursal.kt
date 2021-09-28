package com.example.citassalon.ui.sucursal


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.citassalon.data.models.Sucursal
import com.example.citassalon.data.repository.SucursalRepository
import com.example.citassalon.util.ApiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class ViewModelSucursal @Inject constructor(private val sucursalRepository: SucursalRepository) :
    ViewModel() {

    private var _sucursalLiveData = MutableLiveData<ApiState<List<Sucursal>>>()
    val sucursalLiveData: LiveData<ApiState<List<Sucursal>>>
        get() = _sucursalLiveData

    init {
        getSucursales()
    }

    private fun getSucursales() {
        viewModelScope.launch(Dispatchers.IO) {
            val serviceSucursales = sucursalRepository.getSucursales()
            _sucursalLiveData.postValue(ApiState.Loading(null))
            serviceSucursales.enqueue(object : Callback<List<Sucursal>> {
                override fun onFailure(call: Call<List<Sucursal>>, t: Throwable) {
                    _sucursalLiveData.postValue(ApiState.Error(t, null))
                    Log.v("DEBUG : ", t.message.toString())
                }
                override fun onResponse(
                    call: Call<List<Sucursal>>,
                    response: Response<List<Sucursal>>
                ) {
                    if (response.isSuccessful) {
                        _sucursalLiveData.postValue(ApiState.Success(response.body()!!))
                        Log.v("DEBUG : ", response.body().toString())
                    }
                }
            })
        }
    }

    fun cancelService() {
        viewModelScope.launch(Dispatchers.IO) {
            sucursalRepository.cancelService()
        }
    }

}