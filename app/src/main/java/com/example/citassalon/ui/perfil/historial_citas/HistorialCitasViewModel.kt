package com.example.citassalon.ui.perfil.historial_citas

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.citassalon.data.models.local.Appointment
import com.example.citassalon.data.models.remote.AppointmentResponse
import com.example.citassalon.data.repository.Repository
import com.example.citassalon.data.state.ApiState
import com.example.citassalon.main.NetworkHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class HistorialCitasViewModel @Inject constructor(
    private val appointmentRepository: Repository,
    private val networkHelper: NetworkHelper
) :
    ViewModel() {

    private val _appointment = MutableLiveData<ApiState<List<AppointmentResponse>>>()
    val appointment: MutableLiveData<ApiState<List<AppointmentResponse>>>
        get() = _appointment

    private val _appointmentsLocal = MutableLiveData<ApiState<List<Appointment>>>()
    val appointmentLocal: MutableLiveData<ApiState<List<Appointment>>>
        get() = _appointmentsLocal

    init {
        getAllAppointMents()
    }

    fun getAllAppointMents() {
        _appointment.value = ApiState.Loading()
        viewModelScope.launch {
            if (!networkHelper.isNetworkConnected()) {
                withContext(Dispatchers.Main) {
                    _appointment.value = ApiState.ErrorNetwork()
                }
                return@launch
            }
            try {
                val appointmens = appointmentRepository.getAppointMents()
                if (appointmens.isEmpty()) {
                    _appointment.value = ApiState.NoData()
                    return@launch
                }
                _appointment.value = ApiState.Success(appointmens)
            } catch (e: Exception) {
                _appointment.value = ApiState.Error(e)
            }
        }
    }

    fun getAllAppointMentsLocal() {
        _appointmentsLocal.value = ApiState.Loading()
        viewModelScope.launch(Dispatchers.IO) {
            val listaAppointments = appointmentRepository.getAllAppointment()
            if (listaAppointments.isEmpty()) {
                withContext(Dispatchers.Main) {
                    _appointmentsLocal.value = ApiState.NoData()
                }
                return@launch
            }
            withContext(Dispatchers.Main) {
                _appointmentsLocal.postValue(ApiState.Success(listaAppointments))
            }
        }
    }

    suspend fun removeAponintment(appointment: Appointment): Boolean {
        var isDeleted = 0
        viewModelScope.launch(Dispatchers.IO) {
            isDeleted = appointmentRepository.deleteAppointment(appointment)
        }.join()
        if (isDeleted > 0) {
            Log.w("ANDROID", "Se ha eliminado el registro")
            return true
        }
        return false
    }

}