package com.example.citassalon.presentacion.features.share_beetwen_sucursales


import androidx.lifecycle.viewModelScope
import com.example.citassalon.presentacion.features.base.BaseScreenState
import com.example.citassalon.presentacion.features.base.BaseViewModel
import com.example.citassalon.presentacion.main.NetworkHelper
import com.example.data.Repository
import com.example.data.di.CoroutineDispatchers
import com.example.domain.entities.remote.migration.NegoInfo
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
class BranchViewModel @Inject constructor(
    coroutineDispatchers: CoroutineDispatchers,
    networkHelper: NetworkHelper,
    private val repository: Repository,
) :
    BaseViewModel(coroutineDispatchers, networkHelper) {

    private val _state: MutableStateFlow<BaseScreenState<List<NegoInfo>>> =
        MutableStateFlow(BaseScreenState.Loading())
    val state = _state.asStateFlow()


    init {
        getBranches()
    }

    private fun getBranches() = viewModelScope.launch {
        delay(1L.seconds)
        safeApiCallCompose(state = _state, coroutineDispatchers = coroutineDispatchers) {
            val response = repository.getSucursales()
            withContext(Dispatchers.Main) {
                _state.value = BaseScreenState.Success(response.sucursales)
            }
        }
    }

}