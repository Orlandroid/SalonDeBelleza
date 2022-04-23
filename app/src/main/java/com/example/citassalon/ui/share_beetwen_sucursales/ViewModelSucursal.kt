package com.example.citassalon.ui.share_beetwen_sucursales


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.citassalon.data.models.Sucursal
import com.example.citassalon.data.repository.Repository
import com.example.citassalon.data.state.ApiState
import com.example.citassalon.main.NetworkHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ViewModelSucursal @Inject constructor(
    private val repository: Repository,
    private val networkHelper: NetworkHelper
) :
    ViewModel() {

    private var _sucursal = MutableLiveData<ApiState<List<Sucursal>>>()
    val sucursal: LiveData<ApiState<List<Sucursal>>>
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
            val response = repository.getSucursales()
            if (response.isEmpty()) {
                _sucursal.postValue(ApiState.NoData())
                return@launch
            }
            try {
                _sucursal.postValue(ApiState.Success(response))
            } catch (e: Exception) {
                _sucursal.postValue(ApiState.Error(e))
            }
        }
    }


}