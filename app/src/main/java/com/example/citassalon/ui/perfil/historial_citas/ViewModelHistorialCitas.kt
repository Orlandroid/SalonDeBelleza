package com.example.citassalon.ui.perfil.historial_citas

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.citassalon.data.models.Appointment
import com.example.citassalon.data.repository.Repository
import com.example.citassalon.util.ApiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ViewModelHistorialCitas @Inject constructor(private val appointmentRepository: Repository) :
    ViewModel() {

    private val _appointment = MutableLiveData<ApiState<List<Appointment>>>()
    val appointment: MutableLiveData<ApiState<List<Appointment>>>
        get() = _appointment

    init {
        getAllAppointMents()
    }

    private fun getAllAppointMents() {
        _appointment.value = ApiState.Loading(null)
        viewModelScope.launch(Dispatchers.IO) {
            val listaAppointments = appointmentRepository.getAllAppointment()
            withContext(Dispatchers.Main) {
                _appointment.postValue(ApiState.Success(listaAppointments))
            }
        }
    }

}