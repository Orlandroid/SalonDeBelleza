package com.example.citassalon.presentacion.ui.staff


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.citassalon.presentacion.main.NetworkHelper
import com.example.domain.entities.remote.Staff
import com.example.domain.state.ApiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class StaffViewModel @Inject constructor(
    private val repository: com.example.data.Repository,
    private val networkHelper: NetworkHelper
) :
    ViewModel() {

    private val _staff = MutableLiveData<ApiState<List<Staff>>>()
    val staff: MutableLiveData<ApiState<List<Staff>>>
        get() = _staff

    init {
        //getSttafs()
    }

    fun getSttafs() {
        viewModelScope.launch(Dispatchers.IO) {
            _staff.postValue(ApiState.Loading())
            if (!networkHelper.isNetworkConnected()) {
                _staff.postValue(ApiState.ErrorNetwork())
            }
            try {
                val response = repository.getStaffs()
                if (response.isEmpty()) {
                    _staff.postValue(ApiState.NoData())
                    return@launch
                }
                _staff.postValue(ApiState.Success(response))
            } catch (e: Exception) {
                _staff.postValue(ApiState.Error(e))
            }
        }
    }
}