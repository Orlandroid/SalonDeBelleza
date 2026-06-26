package com.example.citassalon.presentacion.features.profile.historial_citas

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.citassalon.presentacion.features.base.BaseScreenState
import com.example.citassalon.presentacion.features.base.BaseScreenState.OnContent
import com.example.citassalon.presentacion.features.base.BaseScreenState.OnLoading
import com.example.citassalon.presentacion.main.NetworkHelper
import com.example.domain.perfil.Appointment
import com.example.domain.state.getContent
import com.example.domain.state.isError
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


data class AppointmentHistoryUiState(
    val idAppointment: String? = null,
    val showDialog: Boolean = false,
    val appointments: List<Appointment> = emptyList()
)

sealed class AppointmentHistoryEvents {
    data class OnRemove(val idAppointment: String) : AppointmentHistoryEvents()
    object OnAccept : AppointmentHistoryEvents()
    object OnCancel : AppointmentHistoryEvents()
    data class OnAppointmentClicked(val appointment: String) : AppointmentHistoryEvents()
}

sealed class AppointmentHistoryEffects {
    data class NavigateToDetail(val idAppointment: String) : AppointmentHistoryEffects()
}

@HiltViewModel
class AppointmentHistoryViewModel @Inject constructor(
    private val networkHelper: NetworkHelper,
    private val getAppointmentsUse: GetAppointmentsUseCase,
    private val deleteAppointmentUseCase: DeleteAppointmentUseCase
) : ViewModel() {


    private val _state: MutableStateFlow<BaseScreenState<AppointmentHistoryUiState>> =
        MutableStateFlow(OnLoading)
    val state = _state.onStart {
        getAppointments()
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = BaseScreenState.OnLoading
    )

    private val _effects = Channel<AppointmentHistoryEffects>()
    val effects = _effects.receiveAsFlow()


    fun onEvents(event: AppointmentHistoryEvents) {
        when (event) {
            is AppointmentHistoryEvents.OnAccept -> {
                _state.update {
                    OnContent(
                        content = AppointmentHistoryUiState(
                            showDialog = false,
                            idAppointment = null
                        )
                    )
                }
            }

            AppointmentHistoryEvents.OnCancel -> {
                _state.update {
                    OnContent(
                        content = AppointmentHistoryUiState(
                            showDialog = false
                        )
                    )
                }
            }

            is AppointmentHistoryEvents.OnRemove -> {
                deleteAppointment(event.idAppointment)
            }

            is AppointmentHistoryEvents.OnAppointmentClicked -> {
                viewModelScope.launch {
                    _effects.send(AppointmentHistoryEffects.NavigateToDetail(idAppointment = event.appointment))
                }
            }
        }
    }


    private fun getAppointments() = viewModelScope.launch {
        _state.update { OnLoading }

        if (!networkHelper.isNetworkConnected()) {
            _state.update {
                BaseScreenState.OnError(error = Throwable("Network Error"))
            }
            return@launch
        }
        val appointmentsResult = getAppointmentsUse()
        if (appointmentsResult.isError()) {
            _state.update {
                BaseScreenState.OnError(error = Throwable("Network Error"))
            }
            return@launch
        }
        val appointments = appointmentsResult.getContent()
        _state.update {
            OnContent(
                content = AppointmentHistoryUiState(
                    appointments = appointments
                )
            )
        }
    }

    private fun deleteAppointment(idAppointment: String) = viewModelScope.launch {
        val deleteAppointmentResult = deleteAppointmentUseCase(idAppointment)
        if (deleteAppointmentResult.isError()) {
            _state.update {
                BaseScreenState.OnError(error = Throwable("Network Error"))
            }
            return@launch
        }
    }

}