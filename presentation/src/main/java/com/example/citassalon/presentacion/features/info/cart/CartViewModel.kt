package com.example.citassalon.presentacion.features.info.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.citassalon.presentacion.features.base.BaseScreenStateV2
import com.example.citassalon.presentacion.features.info.products.products.ProductScreenEffects
import com.example.citassalon.presentacion.main.NetworkHelper
import com.example.data.di.IoDispatcher
import com.example.domain.entities.db.ProductDb
import com.example.domain.entities.remote.Cart
import com.example.domain.entities.remote.Product
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
    val cart: Cart? = null,
    val product: Product? = null,
    
)

@HiltViewModel
class CartViewModel @Inject constructor(
    private val repository: com.example.data.Repository,
    private val networkHelper: NetworkHelper,
    @param:IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _state: MutableStateFlow<BaseScreenStateV2<CartUiState>> =
        MutableStateFlow(BaseScreenStateV2.OnLoading)
    val state = _state.onStart {
        getCart(1) //Todo  Add injected assited
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        BaseScreenStateV2.OnLoading
    )

    private val _effects = Channel<ProductScreenEffects>()
    val effects = _effects.receiveAsFlow()


    val allIProducts: LiveData<List<ProductDb>> = repository.getAllProducts().asLiveData()

    fun getCart(id: Int) {
        viewModelScope.launch(ioDispatcher + coroutineExceptionHandler) {
            val response = repository.getSingleCart(id)
            _state.update { BaseScreenStateV2.OnContent(content = CartUiState(cart = response)) }
        }
    }

    fun getSingleProduct(id: Int) {
        viewModelScope.launch(ioDispatcher + coroutineExceptionHandler) {
            val response = repository.getSingleProduct(id)
            _state.update { BaseScreenStateV2.OnContent(content = CartUiState(product = response)) }
        }
    }

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
        _state.update { BaseScreenStateV2.OnError(error = exception) }
    }


}