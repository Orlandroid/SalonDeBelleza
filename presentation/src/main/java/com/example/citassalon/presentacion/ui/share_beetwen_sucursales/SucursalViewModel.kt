package com.example.citassalon.presentacion.ui.share_beetwen_sucursales


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.citassalon.presentacion.main.NetworkHelper
import com.example.citassalon.presentacion.ui.base.BaseViewModel
import com.example.data.Repository
import com.example.data.di.CoroutineDispatchers
import com.example.domain.entities.remote.migration.NegoInfo
import com.example.domain.state.ApiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class SucursalViewModel @Inject constructor(
    coroutineDispatchers: CoroutineDispatchers,
    networkHelper: NetworkHelper,
    private val repository: Repository,
) :
    BaseViewModel(coroutineDispatchers, networkHelper) {

    private var _sucursal = MutableLiveData<ApiState<List<NegoInfo>>>()
    val sucursal: LiveData<ApiState<List<NegoInfo>>>
        get() = _sucursal

    init {
        getSucursales()
    }

    private fun getSucursales() {
        viewModelScope.launch {
            safeApiCall(_sucursal, coroutineDispatchers) {
                val response = repository.getSucursales()
                withContext(Dispatchers.Main) {
                    _sucursal.value = ApiState.Success(response.sucursales)
                }
            }
        }
    }


}