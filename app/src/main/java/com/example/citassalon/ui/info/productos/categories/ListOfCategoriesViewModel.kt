package com.example.citassalon.ui.info.productos.categories

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.citassalon.data.repository.Repository
import com.example.citassalon.data.state.ApiState
import com.example.citassalon.main.NetworkHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ListOfCategoriesViewModel @Inject constructor(
    private val repository: Repository,
    private val networkHelper: NetworkHelper
) : ViewModel() {

    private val _categories = MutableLiveData<ApiState<List<String>>>()
    val categories: MutableLiveData<ApiState<List<String>>>
        get() = _categories

    fun getCategories() {
        viewModelScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main) {
                _categories.value = ApiState.Loading()
            }
            if (!networkHelper.isNetworkConnected()) {
                withContext(Dispatchers.Main) {
                    _categories.value = ApiState.ErrorNetwork()
                }
                return@launch
            }
            try {
                val response = repository.getCategories()
                if (response.isEmpty()){
                    _categories.value=ApiState.NoData()
                    return@launch
                }
                withContext(Dispatchers.Main) {
                    _categories.value = ApiState.Success(response)
                }
            } catch (e: Throwable) {
                withContext(Dispatchers.Main) {
                    _categories.value = ApiState.Error(e)
                }
            }
        }
    }

}