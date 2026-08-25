package com.example.wallet.transactions_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.ui.base.BaseScreenState
import com.example.domain.state.getContent
import com.example.domain.state.getErrorMessage
import com.example.domain.state.isSuccess
import com.example.domain.transaction.Transaction
import com.example.domain.transaction.TransactionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds

data class TransactionUiState(
    val transaction: Transaction
)

@HiltViewModel
class TransactionDetailViewmodel
@Inject constructor(
    private val transactionRepository: TransactionRepository
) : ViewModel() {


    private val _state: MutableStateFlow<BaseScreenState<TransactionUiState>> =
        MutableStateFlow(BaseScreenState.OnLoading)

    val state = _state.onStart {
        val transactionsResult =
            transactionRepository.getTransaction("4b9592e2-99df-4d2c-903d-24fee6f83660")
        if (transactionsResult.isSuccess()) {
            val transaction = transactionsResult.getContent()
            _state.update {
                BaseScreenState.OnContent(
                    content = TransactionUiState(
                        transaction = transaction
                    )
                )
            }
        } else {
            _state.update { BaseScreenState.OnError(error = Throwable(transactionsResult.getErrorMessage())) }
        }

    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = BaseScreenState.OnLoading
    )

}