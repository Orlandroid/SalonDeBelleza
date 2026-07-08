package com.example.citassalon.presentacion.features.info.products.categories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.citassalon.presentacion.features.base.BaseScreenState
import com.example.data.di.IoDispatcher
import com.example.data.remote.dummy_json.DummyJsonRepository
import com.example.data.remote.fake_store.FakeStoreRepository
import com.example.data.remote.products.commons.ProductSource
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
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

data class CategoriesUiState(
    val categories: List<String> = emptyList()
)

sealed class CategoriesEvents {
    data class OnCategoryClicked(val source: ProductSource) : CategoriesEvents()
}

sealed class CategoriesEffects {
    data class NavigateToProducts(val source: ProductSource) : CategoriesEffects()
}

enum class KindOfStore {
    FAKE_STORE,
    DUMMY_JSON,
    PLATZY,
    MyDummy
}

@HiltViewModel(assistedFactory = CategoriesViewModelFactory::class)
class CategoriesViewModel @AssistedInject constructor(
    private val fakeStoreRepository: FakeStoreRepository,
    private val dummyJsonRepository: DummyJsonRepository,
    @param:IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    @Assisted private val store: KindOfStore
) : ViewModel() {


    private val _effects = Channel<CategoriesEffects>()
    val effects = _effects.receiveAsFlow()

    private val _state: MutableStateFlow<BaseScreenState<CategoriesUiState>> =
        MutableStateFlow(BaseScreenState.OnLoading)
    val state = _state.onStart {
        when (store) {
            KindOfStore.FAKE_STORE -> {
                getCategoriesFakeStore()
            }

            KindOfStore.DUMMY_JSON -> {
                getCategoriesDummyJson()
            }

            KindOfStore.PLATZY -> {
//                getCategoriesPlatzy()
            }

            KindOfStore.MyDummy -> {
//                getCategoriesMyDummy()
            }
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        BaseScreenState.OnLoading
    )

    fun onEvents(event: CategoriesEvents) {
        when (event) {
            is CategoriesEvents.OnCategoryClicked -> {
                viewModelScope.launch {
                    _effects.send(CategoriesEffects.NavigateToProducts(event.source))
                }
            }
        }
    }

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
        _state.update { BaseScreenState.OnError(error = exception) }
    }

    private fun getCategoriesFakeStore() =
        viewModelScope.launch(ioDispatcher + coroutineExceptionHandler) {
            val categories = fakeStoreRepository.getCategories()
            _state.update { BaseScreenState.OnContent(content = CategoriesUiState(categories)) }
        }

    private fun getCategoriesDummyJson() =
        viewModelScope.launch(ioDispatcher + coroutineExceptionHandler) {
            val categories = dummyJsonRepository.getCategories().map { it.name }
            _state.update { BaseScreenState.OnContent(content = CategoriesUiState(categories)) }
        }

}