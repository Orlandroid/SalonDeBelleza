package com.example.citassalon.ui.staff


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.citassalon.data.models.Staff
import com.example.citassalon.util.ApiState
import com.example.citassalon.util.NetworkHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewModelStaff @Inject constructor(
    private val repository: StaffRepositoryRemote,
    private val networkHelper: NetworkHelper
) :
    ViewModel() {

    private val _staff = MutableLiveData<ApiState<List<Staff>>>()
    val staff: MutableLiveData<ApiState<List<Staff>>>
        get() = _staff

    init {
        getSucursales()
    }

    private fun getSucursales() {
        viewModelScope.launch(Dispatchers.IO) {
            _staff.postValue(ApiState.Loading(null))
            if (networkHelper.isNetworkConnected()) {
                val response = repository.getStaffs()
                if (response.isSuccessful) {
                    _staff.postValue(ApiState.Success(response.body()!!))
                }
            } else {
                _staff.postValue(ApiState.ErrorNetwork("Error conection"))
            }
        }
    }

}