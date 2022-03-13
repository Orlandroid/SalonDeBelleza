package com.example.citassalon.ui.share_beetwen_sucursales


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.citassalon.data.models.rickandmorty.ResultsLocations
import com.example.citassalon.data.repository.Repository
import com.example.citassalon.data.state.ApiState
import com.example.citassalon.util.NetworkHelper
import com.example.citassalon.util.getRandomPage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Este ViewModel es usado por 2 fragments los cuales son
 * /ui/AgendarSucursal
 * /ui/info/InfoSucursal
 * ***/

@HiltViewModel
class ViewModelSucursal @Inject constructor(
    private val repository: Repository,
    private val networkHelper: NetworkHelper
) :
    ViewModel() {

    private var _sucursal = MutableLiveData<ApiState<List<ResultsLocations>>>()
    val sucursal: LiveData<ApiState<List<ResultsLocations>>>
        get() = _sucursal

    init {
        getSucursales()
    }

    fun getSucursales() {
        viewModelScope.launch(Dispatchers.IO) {
            _sucursal.postValue(ApiState.Loading(null))
            if (!networkHelper.isNetworkConnected()) {
                _sucursal.postValue(ApiState.ErrorNetwork())
                return@launch
            }
            val response = repository.getLocation(page = getRandomPage(1,7))
            _sucursal.postValue(ApiState.Success(response.results))
        }
    }


}