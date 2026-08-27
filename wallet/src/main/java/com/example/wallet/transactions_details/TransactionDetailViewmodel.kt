package com.example.wallet.transactions_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.ui.base.BaseScreenState
import com.example.domain.state.getContent
import com.example.domain.state.getErrorMessage
import com.example.domain.state.isSuccess
import com.example.domain.transaction.Transaction
import com.example.domain.transaction.TransactionRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

data class TransactionUiState(
    val transaction: Transaction
)

@HiltViewModel(assistedFactory = TransactionDetailViewModelFactory::class)
class TransactionDetailViewmodel
@AssistedInject constructor(
    private val transactionRepository: TransactionRepository,
    @Assisted private val transactionId: String,
) : ViewModel() {


    private val _state: MutableStateFlow<BaseScreenState<TransactionUiState>> =
        MutableStateFlow(BaseScreenState.OnLoading)

    val state = _state.onStart {
        val transactionsResult =
            transactionRepository.getTransaction(transactionId)
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