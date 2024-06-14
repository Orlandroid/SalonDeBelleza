package com.example.citassalon.presentacion.features.share_beetwen_sucursales


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.citassalon.presentacion.main.NetworkHelper
import com.example.citassalon.presentacion.features.base.BaseViewModel
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

    private var _branches = MutableLiveData<ApiState<List<NegoInfo>>>()
    val branches: LiveData<ApiState<List<NegoInfo>>>
        get() = _branches

    init {
        getSucursales()
    }

    fun getSucursales() = viewModelScope.launch {
        safeApiCall(_branches, coroutineDispatchers) {
            val response = repository.getSucursales()
            withContext(Dispatchers.Main) {
                _branches.value = ApiState.Success(response.sucursales)
            }
        }
    }


}