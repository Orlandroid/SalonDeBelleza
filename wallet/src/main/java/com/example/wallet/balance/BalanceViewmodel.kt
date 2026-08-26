package com.example.wallet.balance

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.ui.base.BaseScreenState
import com.example.domain.state.getContent
import com.example.domain.state.getErrorMessage
import com.example.domain.state.isSuccess
import com.example.domain.use_cases.GetBalanceUseCase
import com.example.domain.wallet.Currency
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

data class BalanceUiState(
    val userName: String = "",
    val balance: Long = 0L,
    val currency: Currency = Currency.USD,
    val createdAtMillis: Long = 0L,
    val userId: String = "",
    val initialsFrom: String? = null
)

@HiltViewModel
class BalanceViewmodel
@Inject constructor(
    private val getBalanceUseCase: GetBalanceUseCase
) : ViewModel() {


    private val _state: MutableStateFlow<BaseScreenState<BalanceUiState>> =
        MutableStateFlow(BaseScreenState.OnLoading)

    val state = _state.onStart {
        val balanceResult = getBalanceUseCase()
        if (balanceResult.isSuccess()) {
            val wallet = balanceResult.getContent()
            _state.update {
                BaseScreenState.OnContent(
                    content = BalanceUiState(
                        balance = wallet.balance,
                        userName = wallet.userName,
                        currency = wallet.currency,
                        createdAtMillis = wallet.createdAtMillis,
                        initialsFrom = initialsFrom(wallet.userName)
                    )
                )
            }
        } else {
            _state.update { BaseScreenState.OnError(error = Throwable(balanceResult.getErrorMessage())) }
        }

    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = BaseScreenState.OnLoading
    )


    private fun initialsFrom(name: String): String =
        name.trim()
            .split(" ")
            .filter { it.isNotBlank() }
            .take(2)
            .joinToString("") { it.first().uppercase() }
}