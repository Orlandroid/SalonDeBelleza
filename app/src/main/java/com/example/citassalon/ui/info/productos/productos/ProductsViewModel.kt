package com.example.citassalon.ui.info.productos.productos

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.citassalon.data.models.Product
import com.example.citassalon.data.repository.Repository
import com.example.citassalon.data.state.ApiState
import com.example.citassalon.main.NetworkHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ProductsViewModel @Inject constructor(
    private val repository: Repository,
    private val networkHelper: NetworkHelper
) : ViewModel() {

    private val _productos = MutableLiveData<ApiState<List<Product>>>()
    val products: LiveData<ApiState<List<Product>>>
        get() = _productos

    fun getProducts(categoria: String) {
        viewModelScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main) {
                _productos.value = ApiState.Loading()
            }
            if (!networkHelper.isNetworkConnected()) {
                withContext(Dispatchers.Main) {
                    _productos.value = ApiState.ErrorNetwork()
                }
                return@launch
            }
            try {
                val response = repository.getProducts(categoria)
                withContext(Dispatchers.Main) {
                    _productos.value = ApiState.Success(response)
                }
            } catch (e: Throwable) {
                withContext(Dispatchers.Main) {
                    _productos.value = ApiState.Error(e)
                }
            }
        }
    }

}