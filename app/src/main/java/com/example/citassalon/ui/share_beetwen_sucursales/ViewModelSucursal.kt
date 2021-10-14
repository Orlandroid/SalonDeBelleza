package com.example.citassalon.ui.share_beetwen_sucursales


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.citassalon.data.models.Sucursal
import com.example.citassalon.data.repository.SucursalRepository
import com.example.citassalon.util.ApiState
import com.example.citassalon.util.NetworkHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewModelSucursal @Inject constructor(
    private val sucursalRepository: SucursalRepository,
    private val networkHelper: NetworkHelper
) :
    ViewModel() {

    private var _sucursal = MutableLiveData<ApiState<List<Sucursal>>>()
    val sucursal: LiveData<ApiState<List<Sucursal>>>
        get() = _sucursal

    init {
        getSucursales()
    }

    private fun getSucursales() {
        viewModelScope.launch(Dispatchers.IO) {
            _sucursal.postValue(ApiState.Loading(null))
            if (networkHelper.isNetworkConnected()) {
                val response = sucursalRepository.getSucursales()
                if (response.isSuccessful) {
                    _sucursal.postValue(ApiState.Success(response.body()!!))
                }
            } else {
                _sucursal.postValue(ApiState.ErrorNetwork("Error conection"))
            }
        }
    }


}