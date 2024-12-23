package com.example.citassalon.presentacion.features.info.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.citassalon.presentacion.features.base.BaseScreenState
import com.example.citassalon.presentacion.main.NetworkHelper
import com.example.domain.entities.db.ProductDb
import com.example.domain.entities.remote.Cart
import com.example.domain.entities.remote.Product
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class CartViewModel @Inject constructor(
    private val repository: com.example.data.Repository,
    private val networkHelper: NetworkHelper
) : ViewModel() {

    private val _cart = MutableLiveData<BaseScreenState<Cart>>()
    val cart = _cart
    private val _product = MutableLiveData<BaseScreenState<Product>>()
    val product = _product

    val allIProducts: LiveData<List<ProductDb>> = repository.getAllProducts().asLiveData()

    fun getCart(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main) {
                _cart.value = BaseScreenState.Loading()
            }
            if (!networkHelper.isNetworkConnected()) {
                _cart.value = BaseScreenState.ErrorNetwork()
                return@launch
            }
            try {
                val response = repository.getSingleCart(id)
                withContext(Dispatchers.Main) {
                    _cart.value = BaseScreenState.Success(response)
                }
            } catch (e: Throwable) {
                _cart.value = BaseScreenState.Error()
            }
        }
    }

    fun getSingleProduct(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main) {
                _product.value = BaseScreenState.Loading()
            }
            if (!networkHelper.isNetworkConnected()) {
                _product.value = BaseScreenState.ErrorNetwork()
                return@launch
            }
            try {
                val response = repository.getSingleProduct(id)
                withContext(Dispatchers.Main) {
                    _product.value = BaseScreenState.Success(response)
                }
            } catch (e: Throwable) {
                withContext(Dispatchers.Main) {
                    _product.value = BaseScreenState.Error()
                }
            }
        }
    }


}