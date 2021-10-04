package com.example.citassalon.ui.confirmarcita

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.citassalon.data.models.Appointment
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewModelAgendarConfirmacion @Inject constructor(private val appointmentRepository: AppointmentRepository) :
    ViewModel() {

    fun saveAppointMent(appointment: Appointment) {
        viewModelScope.launch(Dispatchers.IO) {
            appointmentRepository.addAppointment(appointment)
        }
    }

    fun getApp() {
        viewModelScope.launch(Dispatchers.IO) {
            val citas = appointmentRepository.getAllAppointment()
            Log.w("CITAS", citas.toString())
        }
    }

}