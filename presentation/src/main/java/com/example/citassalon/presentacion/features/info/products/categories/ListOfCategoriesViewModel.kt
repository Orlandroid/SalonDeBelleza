package com.example.citassalon.presentacion.features.info.products.categories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.citassalon.presentacion.features.base.BaseScreenState
import com.example.data.Repository
import com.example.data.di.IoDispatcher
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

data class CategoriesUiState(
    val categories: List<String> = emptyList()
)

sealed class CategoriesEvents {
    data class OnCategoryClicked(val category: String) : CategoriesEvents()
}

sealed class CategoriesEffects {
    data class NavigateToProducts(val category: String) : CategoriesEffects()
}

@HiltViewModel
class ListOfCategoriesViewModel @Inject constructor(
    private val repository: Repository,
    @param:IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {


    private val _effects = Channel<CategoriesEffects>()
    val effects = _effects.receiveAsFlow()

    private val _state: MutableStateFlow<BaseScreenState<CategoriesUiState>> =
        MutableStateFlow(BaseScreenState.OnLoading)
    val state = _state.onStart {
        getCategoriesFakeStore()
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        BaseScreenState.OnLoading
    )

    fun onEvents(event: CategoriesEvents) {
        when (event) {
            is CategoriesEvents.OnCategoryClicked -> {
                viewModelScope.launch {
                    _effects.send(CategoriesEffects.NavigateToProducts(event.category))
                }
            }
        }
    }

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
        _state.update { BaseScreenState.OnError(error = exception) }
    }

    private fun getCategoriesFakeStore() =
        viewModelScope.launch(ioDispatcher + coroutineExceptionHandler) {
            val categories = repository.getCategories()
            _state.update { BaseScreenState.OnContent(content = CategoriesUiState(categories)) }
        }

}