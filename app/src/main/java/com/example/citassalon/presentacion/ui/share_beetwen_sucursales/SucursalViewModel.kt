package com.example.citassalon.presentacion.ui.share_beetwen_sucursales


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.citassalon.data.models.remote.migration.NegoInfo
import com.example.citassalon.data.Repository
import com.example.citassalon.domain.state.ApiState
import com.example.citassalon.presentacion.main.NetworkHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SucursalViewModel @Inject constructor(
    private val repository: Repository,
    private val networkHelper: NetworkHelper
) :
    ViewModel() {

    private var _sucursal = MutableLiveData<ApiState<List<NegoInfo>>>()
    val sucursal: LiveData<ApiState<List<NegoInfo>>>
        get() = _sucursal

    init {
        getSucursales()
    }

    fun getSucursales() {
        viewModelScope.launch(Dispatchers.IO) {
            _sucursal.postValue(ApiState.Loading())
            if (!networkHelper.isNetworkConnected()) {
                _sucursal.postValue(ApiState.ErrorNetwork())
                return@launch
            }
            try {
                val response = repository.getSucursales()
                if (response.sucursales.isEmpty()) {
                    _sucursal.postValue(ApiState.NoData())
                    return@launch
                }
                _sucursal.postValue(ApiState.Success(response.sucursales))
            } catch (e: Exception) {
                _sucursal.postValue(ApiState.Error(e))
            }
        }
    }


}