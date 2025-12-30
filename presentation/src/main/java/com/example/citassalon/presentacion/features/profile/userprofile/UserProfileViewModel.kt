package com.example.citassalon.presentacion.features.profile.userprofile


import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.citassalon.presentacion.features.base.BaseScreenState
import com.example.citassalon.presentacion.features.base.BaseScreenStateV2
import com.example.data.di.IoDispatcher
import com.example.domain.perfil.UserProfileResponse
import com.example.domain.state.getContent
import com.example.domain.state.isSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

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
    @param:IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val getUserInfoUseCase: GetUserInfoUseCase
) : ViewModel() {

    private val _state: MutableStateFlow<BaseScreenStateV2<UserProfileUiState>> =
        MutableStateFlow(BaseScreenStateV2.OnLoading)
    val state = _state.onStart {
        getUserInfo()
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        BaseScreenStateV2.OnLoading
    )

    private val _infoUserState: MutableStateFlow<BaseScreenState<UserProfileResponse>> =
        MutableStateFlow(BaseScreenState.Idle())
    val infoUserState = _infoUserState.asStateFlow()

    private val _remoteImageProfileState: MutableStateFlow<BaseScreenState<String>> =
        MutableStateFlow(BaseScreenState.Idle())
    val remoteImageProfileState = _remoteImageProfileState.asStateFlow()

    private val _localImageState: MutableStateFlow<BaseScreenState<String>> =
        MutableStateFlow(BaseScreenState.Idle())
    val localImageState = _localImageState.asStateFlow()


    private suspend fun getUserInfo() {
        val userInfo = getUserInfoUseCase.invoke()
        if (userInfo.isSuccess()) {
            _state.update { BaseScreenStateV2.OnContent(content = userInfo.getContent()) }
            return
        }
        _state.update { BaseScreenStateV2.OnError(error = Throwable()) }
    }

}