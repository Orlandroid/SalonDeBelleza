package com.example.citassalon.presentacion.features.info.nuestro_staff


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.citassalon.presentacion.features.base.BaseScreenState
import com.example.data.di.IoDispatcher
import com.example.domain.entities.remote.dummyUsers.DummyUsersResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


data class OurStaffyUiState(
    val staffs: DummyUsersResponse
)

@HiltViewModel
class OurStaffViewModel @Inject constructor(
    private val repository: com.example.data.Repository,
    @param:IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {


    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
        _state.update { BaseScreenState.OnError(error = exception) }
    }

    private val _state: MutableStateFlow<BaseScreenState<OurStaffyUiState>> =
        MutableStateFlow(BaseScreenState.OnLoading)
    val state = _state.onStart {
        getStaffsUsers()
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        BaseScreenState.OnLoading
    )

    private fun getStaffsUsers() = viewModelScope.launch(ioDispatcher + coroutineExceptionHandler) {
        val response = repository.getStaffUsers()
        _state.update { BaseScreenState.OnContent(content = OurStaffyUiState(staffs = response)) }
    }


}