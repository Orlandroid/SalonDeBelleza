package com.example.citassalon.presentacion.features.schedule_appointment.schedule_confirmation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.perfil.AppointmentFirebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


sealed class ScheduleAppointmentEffects {
    object NavigateToAppointComplete : ScheduleAppointmentEffects()
}

sealed class ScheduleAppointmentEvents {
    data class OnConfirmationAppointmentAccepted(val appointment: AppointmentFirebase) :
        ScheduleAppointmentEvents()

    object OnConfirmationDialogCancel : ScheduleAppointmentEvents()
    object OnSaveAppointment : ScheduleAppointmentEvents()
}

data class ScheduleAppointmentState(
    val showConfirmationDialog: Boolean = false,
    val showAnimation: Boolean = false,
    val error: String? = null
)

@HiltViewModel
class ConfirmScheduleViewModel @Inject constructor(
    private val firebaseDatabase: FirebaseDatabase,
    private val firebaseAuth: FirebaseAuth
) : ViewModel() {

    companion object {
        private const val APPOINTMENT_REFERENCE = "Appointment"
    }

    private val _uiState: MutableStateFlow<ScheduleAppointmentState> =
        MutableStateFlow(ScheduleAppointmentState())
    val uiState = _uiState.asStateFlow()

    private val _effects = Channel<ScheduleAppointmentEffects>()
    val effects = _effects.receiveAsFlow()

    fun onEvents(event: ScheduleAppointmentEvents) {
        when (event) {
            is ScheduleAppointmentEvents.OnConfirmationAppointmentAccepted -> {
                _uiState.update { it.copy(showConfirmationDialog = false) }
                saveAppointment(event.appointment)
                viewModelScope.launch {
                    _effects.send(ScheduleAppointmentEffects.NavigateToAppointComplete)
                }
            }

            ScheduleAppointmentEvents.OnConfirmationDialogCancel -> {
                _uiState.update { it.copy(showConfirmationDialog = false) }
            }

            ScheduleAppointmentEvents.OnSaveAppointment -> {
                _uiState.update { it.copy(showConfirmationDialog = true) }
            }
        }
    }


    private fun provideFirebaseRealtimeDatabaseReference(
        firebaseDatabase: FirebaseDatabase,
        firebaseAuth: FirebaseAuth
    ): DatabaseReference {
        val uuidUser = firebaseAuth.uid
        return firebaseDatabase.reference.child(APPOINTMENT_REFERENCE)
            .child(uuidUser!!)
    }

    private fun saveAppointment(appointment: AppointmentFirebase) {
        appointment.employee = "Orlando"
        val databaseReference =
            provideFirebaseRealtimeDatabaseReference(firebaseDatabase, firebaseAuth)
        databaseReference.child(appointment.idAppointment).setValue(appointment)
            .addOnSuccessListener {
                print("succes")
                _uiState.update { it.copy(showAnimation = true) }
            }.addOnFailureListener { exception ->
                print("error ${exception.message}")
                _uiState.update { it.copy(error = exception.message) }
            }
    }

}