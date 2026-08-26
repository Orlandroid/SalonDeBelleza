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
import com.example.domain.transaction.TransactionType
import com.example.domain.use_cases.GetCartInfoUseCase
import com.example.domain.use_cases.PurchaseProductsUseCase
import com.example.info.R
import com.example.info.cart.CartEffects.NavigateToProductDetail
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds


sealed class CartEffects {
    data class OnProductsDeleted(val message: String) : CartEffects()
    data class NavigateToProductDetail(val source: ProductSource, val product: Product) :
        CartEffects()

    data object OnPurchaseCompleted : CartEffects()
}

sealed class CartEvents {
    data class OnProductSelect(val source: ProductSource, val product: Product) : CartEvents()
    object OnDeleteIconClicked : CartEvents()
    object OnAccept : CartEvents()
    object OnCancelPressed : CartEvents()
    object OnPay : CartEvents()
    data class OnRemoveProductClicked(val productId: Int) : CartEvents()
    data class OnIncrease(val productId: Int) : CartEvents()
    data class OnDecrease(val productId: Int) : CartEvents()
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
    val showLoadingButton: Boolean = false,
    val error: String? = null,
    val cartTotal: Long = 0L,
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
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        CartUiState()
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
                    _state.update { it.copy(showLoadingButton = true) }
                    delay(0.5.seconds)
                    val description = getDescription()
                    val purchaseResult = purchaseProductsUseCase.invoke(
                        description = description,
                        amount = calculateTotalOfProducts(state.value.products),
                        transactionType = TransactionType.MARKETPLACE_PURCHASE
                    )
                    if (purchaseResult.isSuccess()) {
                        _effects.send(CartEffects.OnPurchaseCompleted)
                    } else {
                        _state.update {
                            it.copy(
                                error = "Purchase failed",
                                showLoadingButton = false
                            )
                        }
                    }
                }
            }

            is CartEvents.OnRemoveProductClicked -> {
                onRemoveProduct(event.productId)
            }

            is CartEvents.OnDecrease -> {
                onDecreaseProduct(event.productId)
            }

            is CartEvents.OnIncrease -> {
                onIncreaseProduct(event.productId)
            }
        }
    }

    private fun calculateTotalOfProducts(products: List<Product>): Long {
        return products.sumOf { it.price }
    }

    private fun getDescription(): String {
        val products = _state.value.products
        val description = when (products.size) {
            1 -> products.first().title
            2 -> products.joinToString(" + ") { it.title }
            else -> "${products.first().title} + ${products.size - 1} more"
        }
        return description
    }

    private fun onRemoveProduct(productId: Int) {
        _state.update { currentState ->
            currentState.copy(
                products = currentState.products.filterNot { product ->
                    product.id == productId
                }
            )
        }
        _state.update { currentState ->
            currentState.copy(cartTotal = getCartTotal(currentState.products))
        }
    }

    private fun onDecreaseProduct(productId: Int) {
        _state.update { currentState ->
            currentState.copy(
                products = currentState.products.map { product ->
                    if (product.id == productId && product.quantity > 1) {
                        product.copy(quantity = product.quantity - 1)
                    } else {
                        product
                    }
                },
            )
        }
        _state.update { currentState ->
            currentState.copy(cartTotal = getCartTotal(currentState.products))
        }
    }

    private fun onIncreaseProduct(productId: Int) {
        _state.update { currentState ->
            currentState.copy(
                products = currentState.products.map { product ->
                    if (product.id == productId) {
                        product.copy(quantity = product.quantity + 1)
                    } else {
                        product
                    }
                }
            )
        }
        _state.update { currentState ->
            currentState.copy(cartTotal = getCartTotal(currentState.products))
        }
    }


    private fun getCartTotal(products: List<Product>): Long {
        var cartTotal = 0L

        products.forEach {
            cartTotal += it.price * it.quantity
        }
        return cartTotal
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
                    isLoading = false,
                    products = userInfo.products,
                    userMoney = userInfo.userMoney,
                    cartTotal = userInfo.cartTotal
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