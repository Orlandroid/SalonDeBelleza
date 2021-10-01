package com.example.citassalon.ui.staff

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.citassalon.data.models.Staff
import com.example.citassalon.data.repository.StaffRepositoryRemote
import com.example.citassalon.util.ApiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class ViewModelStaff @Inject constructor(private val repository: StaffRepositoryRemote) :
    ViewModel() {

    private val _staffLivedata = MutableLiveData<ApiState<List<Staff>>>()
    val staffLiveData: MutableLiveData<ApiState<List<Staff>>>
        get() = _staffLivedata

    init {
        getSucursales()
    }

    private fun getSucursales() {
        viewModelScope.launch(Dispatchers.IO) {
            val serviceServices = repository.getStaffs()
            _staffLivedata.postValue(ApiState.Loading(null))
            serviceServices.enqueue(object : Callback<List<Staff>> {
                override fun onFailure(call: Call<List<Staff>>, t: Throwable) {
                    _staffLivedata.postValue(ApiState.Error(t, null))
                    Log.v("DEBUG : ", t.message.toString())
                }

                override fun onResponse(
                    call: Call<List<Staff>>,
                    response: Response<List<Staff>>
                ) {
                    if (response.isSuccessful) {
                        _staffLivedata.postValue(ApiState.Success(response.body()!!))
                        Log.v("DEBUG : ", response.body().toString())
                    }
                }
            })
        }
    }

    fun cancelService() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getStaffs().cancel()
        }
    }

}