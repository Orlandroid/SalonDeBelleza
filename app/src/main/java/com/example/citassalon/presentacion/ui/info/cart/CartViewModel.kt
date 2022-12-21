package com.example.citassalon.presentacion.ui.info.cart

import androidx.lifecycle.*
import com.example.citassalon.data.db.entities.ProductDb
import com.example.citassalon.data.models.remote.Cart
import com.example.citassalon.data.models.remote.Product
import com.example.citassalon.data.Repository
import com.example.citassalon.domain.state.ApiState
import com.example.citassalon.presentacion.main.NetworkHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class CartViewModel @Inject constructor(
    private val repository: Repository,
    private val networkHelper: NetworkHelper
) : ViewModel() {

    private val _cart = MutableLiveData<ApiState<Cart>>()
    val cart: LiveData<ApiState<Cart>>
        get() = _cart
    private val _product = MutableLiveData<ApiState<Product>>()
    val product: LiveData<ApiState<Product>>
        get() = _product

    val allIProducts: LiveData<List<ProductDb>> = repository.getAllProducts().asLiveData()

    fun getCart(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main) {
                _cart.value = ApiState.Loading()
            }
            if (!networkHelper.isNetworkConnected()) {
                _cart.value = ApiState.ErrorNetwork()
                return@launch
            }
            try {
                val response = repository.getSingleCart(id)
                if (response.products.isEmpty()) {
                    withContext(Dispatchers.Main) {
                        _cart.value = ApiState.NoData()
                    }
                    return@launch
                }
                withContext(Dispatchers.Main) {
                    _cart.value = ApiState.Success(response)
                }
            } catch (e: Throwable) {
                _cart.value = ApiState.Error(e)
            }
        }
    }

    fun getSingleProduct(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main) {
                _product.value = ApiState.Loading()
            }
            if (!networkHelper.isNetworkConnected()) {
                _product.value = ApiState.ErrorNetwork()
                return@launch
            }
            try {
                val response = repository.getSingleProduct(id)
                withContext(Dispatchers.Main) {
                    _product.value = ApiState.Success(response)
                }
            } catch (e: Throwable) {
                withContext(Dispatchers.Main) {
                    _product.value = ApiState.Error(e)
                }
            }
        }
    }


}