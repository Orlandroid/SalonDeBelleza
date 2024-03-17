package com.example.citassalon.presentacion.features.info.productos.productos

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.citassalon.presentacion.main.NetworkHelper
import com.example.citassalon.presentacion.features.base.BaseViewModel
import com.example.data.Repository
import com.example.data.di.CoroutineDispatchers
import com.example.domain.entities.db.ProductDb
import com.example.domain.entities.remote.Product
import com.example.domain.state.ApiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ProductsViewModel @Inject constructor(
    private val repository: Repository,
    coroutineDispatchers: CoroutineDispatchers,
    networkHelper: NetworkHelper,
) : BaseViewModel(coroutineDispatchers, networkHelper) {

    private val _productos = MutableLiveData<ApiState<List<Product>>>()
    val products: LiveData<ApiState<List<Product>>>
        get() = _productos

    companion object {
        private const val NOT_SAVE = -1
    }

    fun getProducts(categoria: String) {
        viewModelScope.launch {
            safeApiCall(_productos, coroutineDispatchers) {
                val response = repository.getProducts(categoria)
                withContext(Dispatchers.Main) {
                    _productos.value = ApiState.Success(response)
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