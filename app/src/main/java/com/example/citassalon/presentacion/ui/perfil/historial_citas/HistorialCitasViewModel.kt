package com.example.citassalon.presentacion.ui.perfil.historial_citas

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.citassalon.data.models.local.Appointment
import com.example.citassalon.domain.state.ApiState
import com.example.citassalon.presentacion.main.NetworkHelper
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import com.example.citassalon.data.models.remote.Appointment as AppointmentRemote


@HiltViewModel
class HistorialCitasViewModel @Inject constructor(
    private val networkHelper: NetworkHelper,
    private val firebaseDatabase: FirebaseDatabase,
    private val firebaseAuth: FirebaseAuth
) :
    ViewModel() {


    private val _appointment =
        MutableLiveData<ApiState<List<AppointmentRemote>>>()
    val appointment: MutableLiveData<ApiState<List<AppointmentRemote>>>
        get() = _appointment


    private val _removeAppointment = MutableLiveData<ApiState<List<Appointment>>>()
    val removeAppointment: MutableLiveData<ApiState<List<Appointment>>>
        get() = _removeAppointment

    init {
        getAppointments()
    }

    companion object {
        private const val APPOINTMENT_REFERENCE = "Appointment"
    }

    private fun provideFirebaseRealtimeDatabaseReference(
        firebaseDatabase: FirebaseDatabase,
        firebaseAuth: FirebaseAuth
    ): DatabaseReference {
        val uuidUser = firebaseAuth.uid
        return firebaseDatabase.reference.child(APPOINTMENT_REFERENCE).child(uuidUser!!)
    }


    private fun getAppointments() {
        _appointment.value = ApiState.Loading()
        viewModelScope.launch(Dispatchers.IO) {
            if (!networkHelper.isNetworkConnected()) {
                withContext(Dispatchers.Main) {
                    _appointment.value = ApiState.ErrorNetwork()
                }
                return@launch
            }
            val databaseReference =
                provideFirebaseRealtimeDatabaseReference(firebaseDatabase, firebaseAuth)
            databaseReference.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val listOfAppointments =
                        arrayListOf<AppointmentRemote>()
                    snapshot.children.forEach {
                        val appointment = it.getValue<AppointmentRemote>()
                        appointment?.let {
                            listOfAppointments.add(appointment)
                        }
                        Log.w("POST", appointment.toString())
                    }
                    if (listOfAppointments.isEmpty()) {
                        _appointment.value = ApiState.NoData()
                    } else {
                        _appointment.value = ApiState.Success(listOfAppointments)
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    _appointment.value = ApiState.Error(Throwable(message = error.message))
                    Log.i("ERROR", error.message)
                }
            })
        }
    }

    fun removeAppointment(idAppointment: String) {
        _removeAppointment.value = ApiState.Loading()
        viewModelScope.launch(Dispatchers.IO) {
            if (!networkHelper.isNetworkConnected()) {
                withContext(Dispatchers.Main) {
                    _removeAppointment.value = ApiState.ErrorNetwork()
                }
                return@launch
            }
            try {
                val databaseReference =
                    provideFirebaseRealtimeDatabaseReference(firebaseDatabase, firebaseAuth)
                databaseReference.child(idAppointment).removeValue().addOnSuccessListener {
                    _removeAppointment.value = ApiState.Success(listOf())
                    Log.i("SUCCES", "Appointment Eliminado")
                }.addOnCanceledListener {
                    _removeAppointment.value =
                        ApiState.Error(Throwable(message = "Error al elimnar"))
                    Log.i("ERROR", "Error al eliminar el appointment")
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    _removeAppointment.value = ApiState.Error(e)
                }
            }
        }
    }

}