package com.example.branches.branches

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.AppointmentSession
import com.example.core.ui.base.BaseScreenState
import com.example.di.IoDispatcher
import com.example.domain.entities.remote.migration.NegoInfo
import com.example.domain.repository.AppointmentsRepository
import com.example.domain.state.getContent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds

sealed class BranchesEffects {
    data object GoToScheduleStaff : BranchesEffects()
    data object GotoBranchInfo : BranchesEffects()
}

@HiltViewModel
class BranchViewModel @Inject constructor(
    private val repository: AppointmentsRepository,
    @param:IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val appointmentSession: AppointmentSession
) : ViewModel() {

    private val _effects = Channel<BranchesEffects>()
    val effects = _effects.receiveAsFlow()

    private val _state: MutableStateFlow<BaseScreenState<List<NegoInfo>>> =
        MutableStateFlow(BaseScreenState.OnLoading)
    val state = _state.onStart { getBranches() }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        BaseScreenState.OnLoading
    )


    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
        _state.update { BaseScreenState.OnError(error = exception) }
    }

    fun onBranchSelected(branch: NegoInfo) {
        appointmentSession.selectBranch(branch)
        viewModelScope.launch {
            _effects.send(BranchesEffects.GoToScheduleStaff)
        }
    }

    private fun getBranches() = viewModelScope.launch(coroutineExceptionHandler + ioDispatcher) {
        delay(1L.seconds)
        val response = repository.getBranches().getContent()
        _state.update { BaseScreenState.OnContent(content = response) }
    }

}