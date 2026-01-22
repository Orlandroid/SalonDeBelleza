package com.example.citassalon.presentacion.features.profile.userprofile


import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.citassalon.presentacion.features.base.BaseScreenState
import com.example.domain.state.getContent
import com.example.domain.state.isSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

sealed class UserProfileEffects {
    object OpenCamera : UserProfileEffects()
}

data class UserProfileUiState(
    val name: String? = null,
    val phone: String? = null,
    val email: String? = null,
    val uid: String? = null,
    val money: String? = null,
    val image: String? = null,
    val statusColor: Color? = null
)

@HiltViewModel
class UserProfileViewModel @Inject constructor(
    private val getUserInfoUseCase: GetUserInfoUseCase
) : ViewModel() {

    private val _state: MutableStateFlow<BaseScreenState<UserProfileUiState>> =
        MutableStateFlow(BaseScreenState.OnLoading)
    val state = _state.onStart {
        getUserInfo()
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        BaseScreenState.OnLoading
    )


    private suspend fun getUserInfo() {
        val userInfo = getUserInfoUseCase.invoke()
        if (userInfo.isSuccess()) {
            _state.update { BaseScreenState.OnContent(content = userInfo.getContent()) }
            return
        }
        _state.update { BaseScreenState.OnError(error = Throwable()) }
    }

}