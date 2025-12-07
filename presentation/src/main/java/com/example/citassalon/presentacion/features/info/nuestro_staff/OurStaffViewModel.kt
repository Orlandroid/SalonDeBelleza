package com.example.citassalon.presentacion.features.info.nuestro_staff


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.citassalon.presentacion.features.base.BaseScreenStateV2
import com.example.domain.entities.remote.dummyUsers.DummyUsersResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


data class OurStaffyUiState(
    val staffs: DummyUsersResponse
)

@HiltViewModel
class OurStaffViewModel @Inject constructor(
    private val repository: com.example.data.Repository
) : ViewModel() {


    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
        _state.update { BaseScreenStateV2.OnError(error = exception) }
    }

    private val _state: MutableStateFlow<BaseScreenStateV2<OurStaffyUiState>> =
        MutableStateFlow(BaseScreenStateV2.OnLoading)
    val state = _state.asStateFlow()

    fun getStaffsUsers() = viewModelScope.launch(coroutineExceptionHandler) {
        val response = repository.getStaffUsers()
        _state.update { BaseScreenStateV2.OnContent(content = OurStaffyUiState(staffs = response)) }
    }


}