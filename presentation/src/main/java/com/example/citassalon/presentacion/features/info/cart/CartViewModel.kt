package com.example.citassalon.presentacion.features.info.cart

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.citassalon.R
import com.example.citassalon.presentacion.features.base.BaseScreenState
import com.example.data.Repository
import com.example.data.di.IoDispatcher
import com.example.data.preferences.LoginPreferences
import com.example.data.remote.products.commons.product.ProductSource
import com.example.domain.entities.remote.products.Product
import com.example.domain.state.isSuccess
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
}

data class CartUiState(
    val products: List<Product> = emptyList(),
    val userMoney: String = "0",
    val showDeleteDialog: Boolean = false
)

@HiltViewModel
class CartViewModel @Inject constructor(
    private val repository: Repository,
    @param:IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val loginPreferences: LoginPreferences,
    @param:ApplicationContext private val context: Context
) : ViewModel() {

    private val _state: MutableStateFlow<BaseScreenState<CartUiState>> =
        MutableStateFlow(BaseScreenState.OnLoading)
    val state = _state.onStart {
        getAllProducts()
        getUserMoney()
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        BaseScreenState.OnLoading
    )

    private val _effects = Channel<CartEffects>()
    val effects = _effects.receiveAsFlow()

    var cachedProducts: List<Product> = emptyList()
    private var cachedUserMoney: String = "0"

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
        _state.update { BaseScreenState.OnError(error = exception) }
    }


    fun onEvents(event: CartEvents) {
        when (event) {
            CartEvents.OnDeleteIconClicked -> {
                _state.update {
                    BaseScreenState.OnContent(
                        content = CartUiState(
                            products = cachedProducts,
                            showDeleteDialog = true
                        )
                    )
                }
            }

            is CartEvents.OnProductSelect -> {
                viewModelScope.launch {
                    _effects.send(
                        CartEffects.NavigateToProductDetail(
                            source = event.source,
                            product = event.product
                        )
                    )
                }
            }

            CartEvents.OnAccept -> {
                _state.update { BaseScreenState.OnLoading }
                deleteAllTheProducts()
                _state.update { BaseScreenState.OnContent(content = CartUiState()) }
            }

            CartEvents.OnCancelPressed -> {
                _state.update {
                    BaseScreenState.OnContent(
                        content = CartUiState(
                            products = cachedProducts,
                            showDeleteDialog = false
                        )
                    )
                }
            }

        }
    }


    fun getAllProducts() {
        viewModelScope.launch(ioDispatcher + coroutineExceptionHandler) {
            val response = repository.getAllProducts()
            cachedProducts = response
            _state.update {
                BaseScreenState.OnContent(
                    content = CartUiState(
                        products = cachedProducts,
                        userMoney = cachedUserMoney
                    )
                )
            }
        }
    }

    fun getUserMoney() {
        viewModelScope.launch {
            cachedUserMoney = loginPreferences.getUserMoney().toString()
            _state.update {
                BaseScreenState.OnContent(
                    content = CartUiState(
                        products = cachedProducts,
                        userMoney = cachedUserMoney
                    )
                )
            }
        }
    }

    private fun deleteAllTheProducts() {
        viewModelScope.launch(ioDispatcher + coroutineExceptionHandler) {
            val result = repository.deleteAllProducts()
            if (result.isSuccess()) {
                _effects.send(CartEffects.OnProductsDeleted(message = context.getString(R.string.products_deleted)))
            }
        }
    }


}