package com.example.citassalon.ui.info.cart

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.citassalon.data.models.Cart
import com.example.citassalon.data.repository.Repository
import com.example.citassalon.data.state.ApiState
import com.example.citassalon.util.NetworkHelper
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


    fun getCart(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main) {
                _cart.value = ApiState.Loading()
            }
            if (!networkHelper.isNetworkConnected()) {
                _cart.value = ApiState.ErrorNetwork()
                return@launch
            }
            val response = repository.getSingleCart(id)
            Log.w("RESPONSE", response.products.toString())
            try {
                if (response.products.isEmpty()) {
                    withContext(Dispatchers.Main) {
                        _cart.value = ApiState.NoData()
                    }
                    return@launch
                }
                withContext(Dispatchers.Main){
                    _cart.value = ApiState.Success(response)
                }
            } catch (e: Throwable) {
                _cart.value=ApiState.Error(e)
            }
        }
    }




}