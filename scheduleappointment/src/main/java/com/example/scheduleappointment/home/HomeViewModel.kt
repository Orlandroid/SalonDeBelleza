package com.example.scheduleappointment.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.state.getContent
import com.example.domain.state.isSuccess
import com.example.domain.use_cases.GetBalanceUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject


data class HomeUiState(
    val balance: Long = 0L
)

sealed class HomeScreenEvents {
    object NavigateToInfoNavigationFlow : HomeScreenEvents()
    object NavigateToChoseBranch : HomeScreenEvents()
    object NavigateToProfile : HomeScreenEvents()
    object OnCloseScreen : HomeScreenEvents()
    object NavigateToWallet : HomeScreenEvents()
}

@HiltViewModel
class HomeViewModel
@Inject constructor(
    private val getBalanceUseCase: GetBalanceUseCase
) : ViewModel() {


    private val _state: MutableStateFlow<HomeUiState> =
        MutableStateFlow(HomeUiState())
    val state = _state.onStart {
        val getBalanceResult = getBalanceUseCase.invoke()
        if (getBalanceResult.isSuccess()) {
            _state.update { it.copy(balance = getBalanceResult.getContent().balance) }
        } else {
            //Todo show some message error in the wallet card
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = HomeUiState()
    )


}