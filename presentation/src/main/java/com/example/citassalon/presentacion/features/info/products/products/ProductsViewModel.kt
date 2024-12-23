package com.example.citassalon.presentacion.features.info.products.products

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.citassalon.presentacion.features.base.BaseViewModel
import com.example.citassalon.presentacion.features.base.BaseScreenState
import com.example.citassalon.presentacion.main.NetworkHelper
import com.example.data.Repository
import com.example.data.di.CoroutineDispatchers
import com.example.domain.entities.db.ProductDb
import com.example.domain.entities.remote.Product
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ProductsViewModel @Inject constructor(
    private val repository: Repository,
    coroutineDispatchers: CoroutineDispatchers,
    networkHelper: NetworkHelper,
) : BaseViewModel(coroutineDispatchers, networkHelper) {

    companion object {
        private const val NOT_SAVE = -1
    }

    private val _products = MutableLiveData<BaseScreenState<List<Product>>>()
    val products: LiveData<BaseScreenState<List<Product>>>
        get() = _products

    private val _state: MutableStateFlow<BaseScreenState<List<Product>>> =
        MutableStateFlow(BaseScreenState.Loading())
    val state = _state.asStateFlow()


    fun getProductsV2(categoria: String) {
        viewModelScope.launch {
            safeApiCallCompose(_state, coroutineDispatchers) {
                val response = repository.getProducts(categoria)
                withContext(Dispatchers.Main) {
                    _state.value = BaseScreenState.Success(response)
                }
            }
        }
    }

    fun insertProduct(item: ProductDb, theProductWasSave: () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            val id = repository.addProduct(item).toInt()
            if (id != NOT_SAVE) {
                theProductWasSave()
            }
        }
    }

    fun deleteAllProducts() {
        viewModelScope.launch {
            repository.deleteAllProducts()
        }
    }

}