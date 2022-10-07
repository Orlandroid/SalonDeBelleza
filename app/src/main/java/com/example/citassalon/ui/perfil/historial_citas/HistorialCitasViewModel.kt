package com.example.citassalon.ui.perfil.historial_citas

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.citassalon.data.models.local.Appointment
import com.example.citassalon.data.repository.Repository
import com.example.citassalon.data.state.ApiState
import com.example.citassalon.main.NetworkHelper
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Named
import com.example.citassalon.data.models.remote.Appointment as AppointmentRemote


@HiltViewModel
class HistorialCitasViewModel @Inject constructor(
    private val appointmentRepository: Repository,
    private val networkHelper: NetworkHelper,
    private val databaseReference: DatabaseReference,
) :
    ViewModel() {

    @Inject
    @Named("firebase_url_user")
    lateinit var urlDatabaseFirebase:String

    private val _appointment =
        MutableLiveData<ApiState<List<AppointmentRemote>>>()
    val appointment: MutableLiveData<ApiState<List<AppointmentRemote>>>
        get() = _appointment

    private val _appointmentsLocal = MutableLiveData<ApiState<List<Appointment>>>()
    val appointmentLocal: MutableLiveData<ApiState<List<Appointment>>>
        get() = _appointmentsLocal

    private val _removeAppointment = MutableLiveData<ApiState<List<Appointment>>>()
    val removeAppointment: MutableLiveData<ApiState<List<Appointment>>>
        get() = _removeAppointment

    init {
        getAppointments()
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
                //_appointment.value = ApiState.Success(appointmens)
            } catch (e: Exception) {
                _appointment.value = ApiState.Error(e)
            }
        }
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
                Log.i("ULR",urlDatabaseFirebase)
                databaseReference.child(idAppointment).removeValue().addOnSuccessListener {
                    _removeAppointment.value = ApiState.Success(listOf())
                    Log.i("SUCCES", "Appointment Eliminado")
                }.addOnCanceledListener {
                    _removeAppointment.value =
                        ApiState.Error(Throwable(message = "Error al elimnar"))
                    Log.i("ERROR", "Error al eliminar el appointment")
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main){
                    _removeAppointment.value = ApiState.Error(e)
                }
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