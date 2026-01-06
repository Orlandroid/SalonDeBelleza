package com.example.citassalon.presentacion.features.share_beetwen_sucursales


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.citassalon.presentacion.features.base.BaseScreenState
import com.example.data.Repository
import com.example.data.di.IoDispatcher
import com.example.domain.entities.remote.migration.NegoInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds


@HiltViewModel
class BranchViewModel @Inject constructor(
    private val repository: Repository,
    @param:IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

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

    private fun getBranches() = viewModelScope.launch(coroutineExceptionHandler + ioDispatcher) {
        delay(1L.seconds)
        val response = repository.getSucursales()
        _state.update { BaseScreenState.OnContent(content = response.sucursales) }

    }

}