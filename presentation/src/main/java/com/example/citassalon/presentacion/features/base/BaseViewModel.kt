package com.example.citassalon.presentacion.features.base

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.citassalon.presentacion.main.NetworkHelper
import com.example.data.di.CoroutineDispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okio.IOException
import retrofit2.HttpException
import java.net.SocketTimeoutException


abstract class BaseViewModel(
    protected val coroutineDispatchers: CoroutineDispatchers,
    val networkHelper: NetworkHelper
) : ViewModel() {

    enum class ErrorType {
        NETWORK,
        TIMEOUT,
        UNKNOWN
    }

    var job: Job? = null
    

    inline fun <T> safeApiCallCompose(
        state: MutableStateFlow<BaseScreenState<T>>,
        coroutineDispatchers: CoroutineDispatchers,
        crossinline apiToCall: suspend () -> Unit,
    ) {
        job?.cancel()
        job = viewModelScope.launch(coroutineDispatchers.io) {
            try {
                if (!networkHelper.isNetworkConnected()) {
                    state.value = BaseScreenState.ErrorNetwork()
                    return@launch
                }
                apiToCall()
            } catch (e: Exception) {
                withContext(coroutineDispatchers.main) {
                    e.printStackTrace()
                    Log.e("ApiCalls", "Call error: ${e.localizedMessage}", e.cause)
                    when (e) {
                        is HttpException -> {
                            state.value = BaseScreenState.Error(exception = e)
                        }

                        is SocketTimeoutException -> state.value =
                            BaseScreenState.Error(exception = e)

                        is IOException -> state.value = BaseScreenState.Error(exception = e)
                        else -> state.value = BaseScreenState.Error(exception = e)
                    }
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }


}



