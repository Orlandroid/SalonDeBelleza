package com.example.profile.userprofile


import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.ui.base.BaseScreenState
import com.example.domain.loyalty.Loyalty
import com.example.domain.loyalty.PromotionCode
import com.example.domain.state.getContent
import com.example.domain.state.isSuccess
import com.example.domain.use_cases.GetUserInfoUseCase
import com.example.profile.mappers.toUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class UserProfileEffects {
    object OpenCamera : UserProfileEffects()
}

data class UserProfileUiState(
    val name: String? = null,
    val phone: String? = null,
    val email: String? = null,
    val uid: String? = null,
    val money: Long? = null,
    val image: String? = null,
    val statusColor: Color? = null,
    val loyalty: Loyalty? = null,
    val coupons: List<PromotionCode> = emptyList()
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


    private fun getUserInfo() = viewModelScope.launch {
        val userInfoResult = getUserInfoUseCase()
        if (userInfoResult.isSuccess()) {
            val profile = userInfoResult.getContent().toUiState()
            _state.update { BaseScreenState.OnContent(content = profile) }
        } else {
            _state.update { BaseScreenState.OnError(error = Throwable()) }
        }
    }

}
