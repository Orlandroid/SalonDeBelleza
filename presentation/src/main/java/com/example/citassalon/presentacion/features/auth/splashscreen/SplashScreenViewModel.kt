package com.example.citassalon.presentacion.features.auth.splashscreen


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.di.IoDispatcher
import com.example.data.preferences.LoginPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

data class SplashScreenUiState(
    val isLoading: Boolean = true,
    val isUserLoggedIn: Boolean = false
)

@HiltViewModel
class SplashScreenViewModel
@Inject constructor(
    private val loginPreferences: LoginPreferences,
    @param:IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _state: MutableStateFlow<SplashScreenUiState> =
        MutableStateFlow(SplashScreenUiState())
    val state = _state.onStart {
        val isUserLoggedIn = loginPreferences.isUserLoggedIn()
        _state.update { it.copy(isUserLoggedIn = isUserLoggedIn, isLoading = false) }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = SplashScreenUiState()
    )


}