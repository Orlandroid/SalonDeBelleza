package com.example.auth.splashscreen


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.UserPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
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
    private val userPreferences: UserPreferences
) : ViewModel() {


    private val _state: MutableStateFlow<SplashScreenUiState> =
        MutableStateFlow(SplashScreenUiState())
    val state = _state.onStart {
        val isUserLoggedIn = userPreferences.isUserLoggedIn()
        _state.update { it.copy(isUserLoggedIn = isUserLoggedIn, isLoading = false) }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = SplashScreenUiState()
    )


}