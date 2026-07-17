package com.example.citassalon.presentacion.features.info.products.products

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.citassalon.R
import com.example.citassalon.presentacion.features.base.BaseScreenState
import com.example.data.Repository
import com.example.data.di.IoDispatcher
import com.example.data.remote.products.CategoryRepository
import com.example.data.remote.products.ProductRepository
import com.example.data.remote.products.commons.product.ProductSource
import com.example.data.remote.products.commons.product.toCategorySource
import com.example.domain.entities.remote.products.Product
import com.example.domain.state.isError
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
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

sealed class ProductScreenEvents {
    object OnCarClicked : ProductScreenEvents()
    data class OnAddProduct(val product: Product) : ProductScreenEvents()
    data class OnProductClicked(val product: Product) : ProductScreenEvents()
    data class OnDeleteAllTheProducts(val product: Product) : ProductScreenEvents()
}

sealed class ProductScreenEffects {
    object NavigateToCar : ProductScreenEffects()
    data class ProductSaved(val message: String) : ProductScreenEffects()
    data class ProductsDeletedSuccessfully(val message: String) : ProductScreenEffects()
    object NoProductsToDelete : ProductScreenEffects()
    data class NavigateToProductDetail(val product: Product, val source: ProductSource) :
        ProductScreenEffects()
}

data class ProductsUiState(
    val products: List<Product>
)


@HiltViewModel(assistedFactory = ProductsViewModelFactory::class)
class ProductsViewModel @AssistedInject constructor(
    private val productRepository: ProductRepository,
    private val categoryRepository: CategoryRepository,
    @param:ApplicationContext private val context: Context,
    private val repository: Repository,
    @param:IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    @Assisted private val source: ProductSource,
    @Assisted private val category: String? = null
) : ViewModel() {

    companion object {
        private const val NOT_SAVE = -1
    }

    private val _state: MutableStateFlow<BaseScreenState<ProductsUiState>> =
        MutableStateFlow(BaseScreenState.OnLoading)
    val state = _state.onStart {
        if (category.isNullOrEmpty()) {
            getProducts()
        } else {
            getProductByCategory()
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        BaseScreenState.OnLoading
    )


    private val _effects = Channel<ProductScreenEffects>()
    val effects = _effects.receiveAsFlow()


    fun onEvents(event: ProductScreenEvents) {
        when (event) {
            is ProductScreenEvents.OnCarClicked -> {
                viewModelScope.launch {
                    sendEffect(ProductScreenEffects.NavigateToCar)
                }
            }

            is ProductScreenEvents.OnProductClicked -> {
                viewModelScope.launch {
                    sendEffect(
                        ProductScreenEffects.NavigateToProductDetail(
                            product = event.product,
                            source = source
                        )
                    )
                }
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
        _state.update { BaseScreenState.OnError(error = exception) }
    }


    fun getProducts() {
        viewModelScope.launch(ioDispatcher + coroutineExceptionHandler) {
            val response = productRepository.getProducts(source)
            _state.update { BaseScreenState.OnContent(content = ProductsUiState(response)) }
        }
    }

    fun getProductByCategory() {
        val categorySource = source.toCategorySource() ?: return
        if (category == null) return
        viewModelScope.launch(ioDispatcher + coroutineExceptionHandler) {
            val response = categoryRepository.getProductByCategory(
                source = categorySource,
                category = category
            )
            _state.update { BaseScreenState.OnContent(content = ProductsUiState(response)) }
        }
    }

    fun insertProduct(item: Product) {
        viewModelScope.launch(ioDispatcher + coroutineExceptionHandler) {
            val id = repository.addProduct(product = item).toInt()
            if (id != NOT_SAVE) {
                _effects.send(ProductScreenEffects.ProductSaved(context.getString(R.string.product_added)))
            }
        }
    }

    fun deleteAllProducts() {
        viewModelScope.launch(ioDispatcher + coroutineExceptionHandler) {
            val deleteProductsResult = repository.deleteAllProducts()
            if (deleteProductsResult.isError()) {
                _effects.send(ProductScreenEffects.NoProductsToDelete)
                return@launch
            }
            _effects.send(ProductScreenEffects.ProductsDeletedSuccessfully(context.getString(R.string.products_deleted)))
        }
    }

    private suspend fun sendEffect(effect: ProductScreenEffects) {
        _effects.send(effect)
    }

}