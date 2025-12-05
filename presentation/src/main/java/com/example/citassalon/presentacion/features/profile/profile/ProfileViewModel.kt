package com.example.citassalon.presentacion.features.profile.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.preferences.LoginPreferences
import com.example.domain.perfil.ProfileItem
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


data class ProfileUiState(
    val showAlertCloseSession: Boolean = false,
    val user: String? = null,
    val menusProfile: List<ProfileItem> = ProfileMenuProvider.getMenusProfile()
)

sealed class ProfileEvents {
    object OnProfileClicked : ProfileEvents()
    object OnHistoricalClicked : ProfileEvents()
    object OnContactClicked : ProfileEvents()
    object OnTermAndCondictionsClicked : ProfileEvents()
    object OnCloseSession : ProfileEvents()
    object OnDismissDialog : ProfileEvents()
    object OnConfirmClicked : ProfileEvents()
    object OnCancel : ProfileEvents()
}

sealed class ProfileEffects {
    object CloseAndOpenActivity : ProfileEffects()
    object NavigateToProfile : ProfileEffects()
    object NavigateToHistory : ProfileEffects()
    object NavigateToContacts : ProfileEffects()
    object NavigateToTermAndCondictions : ProfileEffects()
}

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val repository: com.example.data.Repository,
    private val loginPreferences: LoginPreferences
) :
    ViewModel() {

    private val _uiState: MutableStateFlow<ProfileUiState> =
        MutableStateFlow(ProfileUiState())

    val uiState = _uiState.onStart {
        _uiState.update { it.copy(user = repository.getUser()?.email) }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(500L),
        initialValue = ProfileUiState()
    )

    private val _effects = Channel<ProfileEffects>()

    val effects = _effects.receiveAsFlow()

    fun onEvents(event: ProfileEvents) {
        when (event) {
            ProfileEvents.OnCloseSession -> {
                _uiState.update { it.copy(showAlertCloseSession = true) }
            }

            ProfileEvents.OnContactClicked -> {
                sendEffect(ProfileEffects.NavigateToContacts)
            }

            ProfileEvents.OnHistoricalClicked -> {
                sendEffect(ProfileEffects.NavigateToHistory)
            }

            ProfileEvents.OnProfileClicked -> {
                sendEffect(ProfileEffects.NavigateToProfile)
            }

            ProfileEvents.OnTermAndCondictionsClicked -> {
                sendEffect(ProfileEffects.NavigateToTermAndCondictions)
            }

            ProfileEvents.OnDismissDialog -> {
                _uiState.update { it.copy(showAlertCloseSession = false) }
                destroyUserSession()
                logout()
                viewModelScope.launch {
                    _effects.send(ProfileEffects.CloseAndOpenActivity)
                }
            }

            ProfileEvents.OnConfirmClicked -> {
                _uiState.update { it.copy(showAlertCloseSession = false) }
            }

            ProfileEvents.OnCancel -> {
                _uiState.update { it.copy(showAlertCloseSession = false) }
            }
        }
    }

    private fun sendEffect(effect: ProfileEffects) {
        viewModelScope.launch {
            _effects.send(effect)
        }
    }

    private fun destroyUserSession() {
        loginPreferences.destroyUserSession()
    }

    private fun logout() {
        repository.logout()
    }

}