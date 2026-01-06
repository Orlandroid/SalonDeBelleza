package com.example.citassalon.presentacion.features.info.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.citassalon.presentacion.features.base.BaseScreenState
import com.example.citassalon.presentacion.features.info.products.products.ProductScreenEffects
import com.example.data.di.IoDispatcher
import com.example.domain.entities.ProductUi
import com.example.domain.entities.toProductUiList
import dagger.hilt.android.lifecycle.HiltViewModel
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


sealed class CartEvents {
    object OnProductSelect : CartEvents()
}

data class CartUiState(
    val products: List<ProductUi> = emptyList()
)

@HiltViewModel
class CartViewModel @Inject constructor(
    private val repository: com.example.data.Repository,
    @param:IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _state: MutableStateFlow<BaseScreenState<CartUiState>> =
        MutableStateFlow(BaseScreenState.OnLoading)
    val state = _state.onStart {
        getAllProducts()
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        BaseScreenState.OnLoading
    )

    private val _effects = Channel<ProductScreenEffects>()
    val effects = _effects.receiveAsFlow()


    fun getAllProducts() {
        viewModelScope.launch(ioDispatcher + coroutineExceptionHandler) {
            val response = repository.getAllProducts()
            _state.update { BaseScreenState.OnContent(content = CartUiState(products = response.toProductUiList())) }
        }
    }

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
        _state.update { BaseScreenState.OnError(error = exception) }
    }


}