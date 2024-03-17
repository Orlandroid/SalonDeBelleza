package com.example.citassalon.presentacion.features.staff


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.citassalon.presentacion.main.NetworkHelper
import com.example.citassalon.presentacion.features.base.BaseViewModel
import com.example.data.Repository
import com.example.data.di.CoroutineDispatchers
import com.example.domain.entities.remote.Staff
import com.example.domain.state.ApiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class StaffViewModel @Inject constructor(
    private val repository: Repository,
    coroutineDispatchers: CoroutineDispatchers,
    networkHelper: NetworkHelper,
) :
    BaseViewModel(coroutineDispatchers, networkHelper) {

    private val _staff = MutableLiveData<ApiState<List<Staff>>>()
    val staff: MutableLiveData<ApiState<List<Staff>>>
        get() = _staff

    init {
        //getSttafs()
    }

    fun getSttafs() {
        viewModelScope.launch {
            safeApiCall(_staff, coroutineDispatchers) {
                val response = repository.getStaffs()
                withContext(Dispatchers.Main) {
                    _staff.value = ApiState.Success(response)
                }
            }
        }
    }
}