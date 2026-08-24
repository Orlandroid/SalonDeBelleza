package com.example.info.cart

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.di.IoDispatcher
import com.example.domain.ProductSource
import com.example.domain.entities.remote.products.Product
import com.example.domain.repository.BusinessRepository
import com.example.domain.state.getContent
import com.example.domain.state.getErrorMessage
import com.example.domain.state.isError
import com.example.domain.state.isSuccess
import com.example.domain.use_cases.GetCartInfoUseCase
import com.example.domain.use_cases.PurchaseProductsUseCase
import com.example.info.R
import com.example.info.cart.CartEffects.NavigateToProductDetail
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


sealed class CartEffects {
    data class OnProductsDeleted(val message: String) : CartEffects()
    data class NavigateToProductDetail(val source: ProductSource, val product: Product) :
        CartEffects()
}

sealed class CartEvents {
    data class OnProductSelect(val source: ProductSource, val product: Product) : CartEvents()
    object OnDeleteIconClicked : CartEvents()
    object OnAccept : CartEvents()
    object OnCancelPressed : CartEvents()
    object OnPay : CartEvents()
    object OnRemoveProductClicked : CartEvents()
}

sealed class PurchaseProductsError {
    data object InsufficientBalance : PurchaseProductsError()
    data object BalanceUpdateFailed : PurchaseProductsError()
    data object TransactionCreationFailed : PurchaseProductsError()
}

data class CartUiState(
    val products: List<Product> = emptyList(),
    val userMoney: Long = 0L,
    val showDeleteDialog: Boolean = false,
    val isLoading: Boolean = true,
    val error: String? = null
)

@HiltViewModel
class CartViewModel @Inject constructor(
    @param:IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    @param:ApplicationContext private val context: Context,
    private val repository: BusinessRepository,
    private val purchaseProductsUseCase: PurchaseProductsUseCase,
    private val getCartInfoUseCase: GetCartInfoUseCase
) : ViewModel() {

    private val _state: MutableStateFlow<CartUiState> = MutableStateFlow(CartUiState())
    val state = _state.onStart {
        getCartInfo()
    }.stateIn(
        scope = viewModelScope, started = SharingStarted.WhileSubscribed(5000L), CartUiState()
    )

    private val _effects = Channel<CartEffects>()
    val effects = _effects.receiveAsFlow()

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
        _state.update { it.copy(error = exception.message) }
    }


    fun onEvents(event: CartEvents) {
        when (event) {
            CartEvents.OnDeleteIconClicked -> {
                _state.update {
                    it.copy(showDeleteDialog = true)
                }
            }

            is CartEvents.OnProductSelect -> {
                viewModelScope.launch {
                    _effects.send(
                        NavigateToProductDetail(
                            source = event.source, product = event.product
                        )
                    )
                }
            }

            CartEvents.OnAccept -> {
                deleteAllTheProducts()
            }

            CartEvents.OnCancelPressed -> {
                _state.update {
                    it.copy(showDeleteDialog = false)
                }
            }

            CartEvents.OnPay -> {
                viewModelScope.launch {
                    val purchaseResult = purchaseProductsUseCase.invoke(
                        products = state.value.products, description = ""
                    )
                    if (purchaseResult.isSuccess()) {
                        //Show animation of succes
                    } else {
                        //Show one kinkd or message error
                    }
                }
            }

            CartEvents.OnRemoveProductClicked -> {

            }
        }
    }


    fun getCartInfo() {
        viewModelScope.launch(ioDispatcher + coroutineExceptionHandler) {
            val userInfoResult = getCartInfoUseCase()
            if (userInfoResult.isError()) {
                _state.update { it.copy(error = userInfoResult.getErrorMessage()) }
                return@launch
            }
            val userInfo = userInfoResult.getContent()
            _state.update {
                it.copy(
                    isLoading = false, products = userInfo.products, userMoney = userInfo.userMoney
                )
            }
        }
    }

    private fun deleteAllTheProducts() {
        viewModelScope.launch(ioDispatcher + coroutineExceptionHandler) {
            _state.update { it.copy(isLoading = true) }
            val result = repository.deleteAllProducts()
            if (result.isSuccess()) {
                _effects.send(CartEffects.OnProductsDeleted(message = context.getString(R.string.products_deleted)))
                _state.update {
                    it.copy(
                        isLoading = false,
                        showDeleteDialog = false,
                        products = emptyList()
                    )
                }
            }
        }
    }


}