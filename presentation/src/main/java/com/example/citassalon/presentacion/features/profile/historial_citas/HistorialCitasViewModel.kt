package com.example.citassalon.presentacion.features.profile.historial_citas

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.citassalon.presentacion.features.base.BaseScreenState
import com.example.citassalon.presentacion.main.NetworkHelper
import com.example.domain.perfil.AppointmentFirebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds


@HiltViewModel
class HistorialCitasViewModel @Inject constructor(
    private val networkHelper: NetworkHelper,
    private val firebaseDatabase: FirebaseDatabase,
    private val firebaseAuth: FirebaseAuth
) : ViewModel() {


    private val _appointment = MutableLiveData<BaseScreenState<List<AppointmentFirebase>>>()
    val appointment = _appointment

    private val _removeAppointment = MutableLiveData<BaseScreenState<List<AppointmentFirebase>>>()
    val removeAppointment = _removeAppointment

    private val _state: MutableStateFlow<BaseScreenState<List<AppointmentFirebase>>> =
        MutableStateFlow(BaseScreenState.Loading())
    val state = _state.asStateFlow()

    init {
        getAppointments()
    }

    companion object {
        private const val APPOINTMENT_REFERENCE = "Appointment"
    }

    private fun provideFirebaseRealtimeDatabaseReference(
        firebaseDatabase: FirebaseDatabase, firebaseAuth: FirebaseAuth
    ): DatabaseReference {
        val uuidUser = firebaseAuth.uid
        return firebaseDatabase.reference.child(APPOINTMENT_REFERENCE).child(uuidUser!!)
    }


    private fun getAppointments() = viewModelScope.launch {
        delay(2.seconds)
        _state.value = BaseScreenState.Loading()
        if (!networkHelper.isNetworkConnected()) {
            withContext(Dispatchers.Main) {
                _state.value = BaseScreenState.ErrorNetwork()
            }
            return@launch
        }
        val databaseReference =
            provideFirebaseRealtimeDatabaseReference(firebaseDatabase, firebaseAuth)
        databaseReference.addValueEventListener(
            object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val listOfAppointments = arrayListOf<AppointmentFirebase>()
                    snapshot.children.forEach {
                        val appointment = it.getValue<AppointmentFirebase>()
                        appointment?.let {
                            listOfAppointments.add(appointment)
                        }
                    }
                    if (listOfAppointments.isEmpty()) {
                        _state.value = BaseScreenState.Success(listOfAppointments)
                    } else {
                        _state.value = BaseScreenState.Success(listOfAppointments)
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    _state.value = BaseScreenState.Error(exception = Exception(error.message))
                }
            }
        )
    }

    fun removeAppointment(idAppointment: String) = viewModelScope.launch {
        _state.value = BaseScreenState.Loading()
        delay(2.seconds)
        if (!networkHelper.isNetworkConnected()) {
            withContext(Dispatchers.Main) {
                _state.value = BaseScreenState.ErrorNetwork()
            }
            return@launch
        }
        try {
            val databaseReference =
                provideFirebaseRealtimeDatabaseReference(firebaseDatabase, firebaseAuth)
            databaseReference.child(idAppointment).removeValue().addOnSuccessListener {
                _removeAppointment.value = BaseScreenState.Success(listOf())
            }.addOnCanceledListener {
                _state.value = BaseScreenState.Error(Exception())
            }
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                _state.value = BaseScreenState.Error(e)
            }
        }
    }

}