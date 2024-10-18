package com.example.citassalon.presentacion.features.info.products.categories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.citassalon.presentacion.features.base.BaseViewModel
import com.example.citassalon.presentacion.features.schedule_appointment.branches.BaseScreenState
import com.example.citassalon.presentacion.main.NetworkHelper
import com.example.data.Repository
import com.example.data.di.CoroutineDispatchers
import com.example.data.remote.products.ProductsRepository
import com.example.domain.state.ApiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ListOfCategoriesViewModel @Inject constructor(
    coroutineDispatchers: CoroutineDispatchers,
    private val repository: Repository,
    private val productsRepository: ProductsRepository,
    networkHelper: NetworkHelper
) : BaseViewModel(coroutineDispatchers, networkHelper) {

    var wasCallService = false

    private val _state: MutableStateFlow<BaseScreenState<List<String>>> =
        MutableStateFlow(BaseScreenState.Loading())
    val state = _state.asStateFlow()

    private val _categories = MutableLiveData<ApiState<List<String>>>()
    val categories: MutableLiveData<ApiState<List<String>>>
        get() = _categories

    private val _categoriesResponse = MutableLiveData<ApiState<List<String>>>()
    val categoriesResponse: LiveData<ApiState<List<String>>> get() = _categoriesResponse


    fun getCategoriesFakeStoreV2() = viewModelScope.launch(Dispatchers.IO) {
        safeApiCallCompose(_state, coroutineDispatchers) {
            delay(3000)
            val response = repository.getCategories()
            withContext(Dispatchers.Main) {
                _state.value = BaseScreenState.Success(response)
            }
        }
    }


    fun getCategoriesDummyJson() = viewModelScope.launch {
        safeApiCall(_categoriesResponse, coroutineDispatchers) {
            val response = productsRepository.getCategories()
            withContext(Dispatchers.Main) {
                _categoriesResponse.value = ApiState.Success(response)
            }
        }
    }


}