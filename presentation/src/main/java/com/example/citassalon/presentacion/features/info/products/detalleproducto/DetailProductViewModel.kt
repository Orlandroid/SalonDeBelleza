package com.example.citassalon.presentacion.features.info.products.detalleproducto

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.citassalon.presentacion.features.base.BaseScreenState
import com.example.data.di.IoDispatcher
import com.example.data.remote.fake_store.FakeStoreRepository
import com.example.domain.entities.remote.FakeStoreProduct
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


data class ProductsDetailUiState(
    val product: FakeStoreProduct
)

@HiltViewModel(assistedFactory = ProductDetailViewModelFactory::class)
class DetailProductViewModel @AssistedInject constructor(
    private val fakeStoreRepository: FakeStoreRepository,
    @param:IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    @Assisted private val productId: Int
) : ViewModel() {

    private val _state: MutableStateFlow<BaseScreenState<ProductsDetailUiState>> =
        MutableStateFlow(BaseScreenState.OnLoading)

    val state = _state.onStart {
        getSingleProduct(productId)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        BaseScreenState.OnLoading
    )


    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
        _state.update { BaseScreenState.OnError(error = exception) }
    }

    private fun getSingleProduct(id: Int) {
        viewModelScope.launch(ioDispatcher + coroutineExceptionHandler) {
            val product = fakeStoreRepository.getSingleProduct(id)
            _state.update { BaseScreenState.OnContent(content = ProductsDetailUiState(product = product)) }
        }
    }
}
