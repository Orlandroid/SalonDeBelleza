package com.example.loyalty.loyalityTransactions


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.loyalty.LoyaltyTransaction
import com.example.domain.repository.LoyaltyRepository
import com.example.domain.state.ApiResult
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class LoyaltyHistoryUiState(
    val transactions: List<LoyaltyTransaction> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

@HiltViewModel
class LoyaltyHistoryViewModel @Inject constructor(
    private val repository: LoyaltyRepository,
    private val auth: FirebaseAuth
) : ViewModel() {

    private val _state = MutableStateFlow(LoyaltyHistoryUiState())
    val state: StateFlow<LoyaltyHistoryUiState> = _state.asStateFlow()

    init {
        fetchHistory()
    }

    private fun fetchHistory() = viewModelScope.launch {
        val userId = auth.uid ?: return@launch
        _state.update { it.copy(isLoading = true) }

        val result = repository.getLoyaltyTransactions(userId = userId)

        if (result is ApiResult.Success) {
            // Sort by date (newest first)
            val sortedList = result.result.sortedByDescending { it.createdAt }
            _state.update { it.copy(transactions = sortedList, isLoading = false) }
        } else {
            _state.update { it.copy(isLoading = false, error = "Error al cargar el historial") }
        }
    }
}