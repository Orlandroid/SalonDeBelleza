package com.example.citassalon.presentacion.features.info.nuestro_staff


import androidx.lifecycle.viewModelScope
import com.example.citassalon.presentacion.features.base.BaseViewModel
import com.example.citassalon.presentacion.features.base.BaseScreenState
import com.example.citassalon.presentacion.main.NetworkHelper
import com.example.data.di.CoroutineDispatchers
import com.example.domain.entities.remote.dummyUsers.DummyUsersResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class OurStaffViewModel @Inject constructor(
    coroutineDispatchers: CoroutineDispatchers,
    networkHelper: NetworkHelper,
    private val repository: com.example.data.Repository
) : BaseViewModel(coroutineDispatchers, networkHelper) {

    private val _state: MutableStateFlow<BaseScreenState<DummyUsersResponse>> =
        MutableStateFlow(BaseScreenState.Loading())
    val state = _state.asStateFlow()

    fun getStaffsUsersV2() = viewModelScope.launch {
        safeApiCallCompose(_state, coroutineDispatchers) {
            val response = repository.getStaffUsers()
            withContext(Dispatchers.Main) {
                _state.value = BaseScreenState.Success(response)
            }
        }
    }


}