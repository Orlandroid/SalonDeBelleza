package com.example.loyalty.rewards

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.loyalty.Reward
import com.example.domain.repository.LoyaltyRepository
import com.example.domain.state.ApiResult
import com.example.domain.state.getContent
import com.example.domain.state.isSuccess
import com.example.domain.use_cases.loyalty.RedeemRewardUseCase
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class RewardsUiState(
    val userBalance: Int = 0,
    val rewards: List<Reward> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

sealed class RewardsEvents {
    object OnLoadRewards : RewardsEvents()
    data class OnRedeemReward(val reward: Reward) : RewardsEvents()
}

sealed class RewardsEffects {
    data class ShowSuccess(val promoCode: String, val discount: Int) : RewardsEffects()
    data class ShowError(val message: String) : RewardsEffects()
}

@HiltViewModel
class RewardsViewModel @Inject constructor(
    private val redeemRewardUseCase: RedeemRewardUseCase,
    private val loyaltyRepository: LoyaltyRepository,
     val auth: FirebaseAuth
) : ViewModel() {

    private val userId = auth.uid ?: ""

    private val _state = MutableStateFlow(RewardsUiState())
    val state: StateFlow<RewardsUiState> = _state.asStateFlow()

    private val _effects = Channel<RewardsEffects>()
    val effects = _effects.receiveAsFlow()

    init {
        onEvents(RewardsEvents.OnLoadRewards)
        getUserLoyalty()
    }

    fun onEvents(event: RewardsEvents) {
        when (event) {
            is RewardsEvents.OnLoadRewards -> fetchRewards()
            is RewardsEvents.OnRedeemReward -> redeemReward(event.reward)
        }
    }

    private fun getUserLoyalty() = viewModelScope.launch {
        val loyaltyBalanceResult = loyaltyRepository.getLoyalty(userId)
        if (loyaltyBalanceResult.isSuccess()) {
            _state.update {
                it.copy(
                    userBalance = loyaltyBalanceResult.getContent().balance,
                    isLoading = false
                )
            }
        }
    }

    private fun fetchRewards() = viewModelScope.launch {
        _state.update { it.copy(isLoading = true) }
        val result = loyaltyRepository.getRewards()
        if (result is ApiResult.Success) {
            _state.update { it.copy(rewards = result.result, isLoading = false) }
        } else {
            _state.update { it.copy(isLoading = false, error = "Error al cargar recompensas") }
        }
    }

    private fun redeemReward(reward: Reward) = viewModelScope.launch {
        _state.update { it.copy(isLoading = true) }
        val result = redeemRewardUseCase(userId, reward)
        _state.update { it.copy(isLoading = false) }

        if (result is ApiResult.Success) {
            val promo = result.result
            _effects.send(RewardsEffects.ShowSuccess(promo.code, promo.discountPercentage))
        } else {
            val errorMsg = (result as? ApiResult.Error)?.error ?: "Error desconocido"
            _effects.send(RewardsEffects.ShowError(errorMsg))
        }
    }
}
