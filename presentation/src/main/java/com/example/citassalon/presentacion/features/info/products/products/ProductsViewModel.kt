package com.example.citassalon.presentacion.features.info.products.products

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.citassalon.presentacion.features.base.BaseScreenStateV2
import com.example.citassalon.presentacion.features.info.products.products.ProductScreenEffects.NavigateToProductDetail
import com.example.data.Repository
import com.example.data.di.IoDispatcher
import com.example.domain.entities.db.ProductDb
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

sealed class ProductScreenEvents {
    object OnCarClicked : ProductScreenEvents()
    data class OnAddProduct(val product: ProductDb) : ProductScreenEvents()
    data class OnProductClicked(val product: Product) : ProductScreenEvents()
    data class OnDeleteAllTheProducts(val product: Product) : ProductScreenEvents()
}

sealed class ProductScreenEffects {
    object NavigateToCar : ProductScreenEffects()
    object ProductSaved : ProductScreenEffects()
    data class NavigateToProductDetail(val product: Product) : ProductScreenEffects()
}

data class ProductsUiState(
    val products: List<Product>
)


@HiltViewModel
class ProductsViewModel @Inject constructor(
    private val repository: Repository,
    @param:IoDispatcher private val ioDispatcher: CoroutineDispatcher
//    category: String // Todo add assited viewmodel to pass the category from the other screen
) : ViewModel() {

    companion object {
        private const val NOT_SAVE = -1
    }

    private val _state: MutableStateFlow<BaseScreenStateV2<ProductsUiState>> =
        MutableStateFlow(BaseScreenStateV2.OnLoading)
    val state = _state.onStart {
        getProducts("electronics")
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        BaseScreenStateV2.OnLoading
    )


    private val _effects = Channel<ProductScreenEffects>()
    val effects = _effects.receiveAsFlow()


    fun onEvents(event: ProductScreenEvents) {
        when (event) {
            is ProductScreenEvents.OnCarClicked -> {
                sendEffect(ProductScreenEffects.NavigateToCar)
            }

            is ProductScreenEvents.OnProductClicked -> {
                sendEffect(NavigateToProductDetail(product = event.product))
            }

            is ProductScreenEvents.OnAddProduct -> {
                insertProduct(event.product)
            }

            is ProductScreenEvents.OnDeleteAllTheProducts -> {
                deleteAllProducts()
            }
        }
    }


    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
        _state.update { BaseScreenStateV2.OnError(error = exception) }
    }


    fun getProducts(categoria: String) {
        viewModelScope.launch(ioDispatcher + coroutineExceptionHandler) {
            val response = repository.getProducts(categoria)
            _state.update { BaseScreenStateV2.OnContent(content = ProductsUiState(response)) }
        }
    }

    fun insertProduct(item: ProductDb) {
        viewModelScope.launch(ioDispatcher + coroutineExceptionHandler) {
            val id = repository.addProduct(item).toInt()
            if (id != NOT_SAVE) {
                _effects.send(ProductScreenEffects.ProductSaved)
            }
        }
    }

    fun deleteAllProducts() {
        viewModelScope.launch(ioDispatcher + coroutineExceptionHandler) {
            repository.deleteAllProducts()//Todo add one effect if the date was removed succesfully
        }
    }

    private fun sendEffect(effect: ProductScreenEffects) {
        viewModelScope.launch {
            _effects.send(effect)
        }
    }

}