package com.example.citassalon.presentacion.features.profile.historial_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.citassalon.presentacion.features.base.BaseScreenState
import com.example.data.remote.appointments.AppointmentsRepository
import com.example.domain.entities.local.AppointmentObject
import com.example.domain.state.ApiResult
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


data class HistoryDetailUiState(
    val appointment: AppointmentObject? = null
)

sealed class HistoryDetailEvents {
    object OnProfileClicked : HistoryDetailEvents()
}

@HiltViewModel(assistedFactory = HistoryDetailViewModelFactory::class)
class HistoryDetailViewModel @AssistedInject constructor(
    private val appointmentsRepository: AppointmentsRepository,
    @Assisted private val appointmentId: String
) :
    ViewModel() {

    private val _uiState: MutableStateFlow<BaseScreenState<HistoryDetailUiState>> =
        MutableStateFlow(BaseScreenState.OnLoading)

    val uiState = _uiState.onStart {
        getAppointment(appointmentId = appointmentId)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(500L),
        initialValue = HistoryDetailUiState()
    )


    fun onEvents(event: HistoryDetailEvents) {
        when (event) {
            HistoryDetailEvents.OnProfileClicked -> {

            }
        }
    }

    private fun getAppointment(appointmentId: String) {
        viewModelScope.launch {
            when (val result = appointmentsRepository.getSingleAppointment(appointmentId)) {
                is ApiResult.Success -> {
                    _uiState.update {
                        BaseScreenState.OnContent(
                            content = HistoryDetailUiState(appointment = result.result)
                        )
                    }
                }

                is ApiResult.Error -> {
                    _uiState.update {
                        BaseScreenState.OnError(
                            error = Throwable("Error get appointment")
                        )
                    }
                }
            }
        }
    }

}