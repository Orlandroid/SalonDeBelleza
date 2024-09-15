package com.example.citassalon.presentacion.features.extensions

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.rememberCoroutineScope
import androidx.fragment.app.Fragment
import com.example.domain.state.ApiState
import com.example.domain.state.SessionStatus
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@Composable
fun <T> Fragment.GenericResultState(
    state: State<ApiState<T>?>,
    shouldCloseTheViewOnApiError: Boolean = false,
    onLoading: () -> Unit = { },
    onFinishLoading: () -> Unit = { },
    onSuccess: @Composable (data: T) -> Unit,
) {
    if (state.value is ApiState.Loading) {
        onLoading()
    } else {
        onFinishLoading()
    }
    when (state.value) {
        is ApiState.Error -> {
//            hideProgress()
            showErrorApi()
        }

        is ApiState.ErrorNetwork -> {
//            hideProgress()
            showErrorNetwork(shouldCloseTheViewOnApiError)
        }

        is ApiState.Loading -> {
//            showProgress()
        }

        is ApiState.NoData -> {

        }

        is ApiState.Success -> {
//            hideProgress()
            state.value?.data?.let { dataResponse ->
                onSuccess(dataResponse)
            }
        }

        null -> {}
    }
}


@Composable
fun Fragment.ObserveSessionStatusFlow(
    state: StateFlow<SessionStatus>,
    errorMessage: String,
    onSuccess: () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    LaunchedEffect(Unit) {
        coroutineScope.launch {
            state.collectLatest {
                when (it) {
                    SessionStatus.ERROR -> {
                        showErrorApi(messageBody = errorMessage)
//                        hideProgress()
                    }

                    SessionStatus.LOADING -> {
//                        showProgress()
                    }

                    SessionStatus.NETWORKERROR -> {
                        showErrorNetwork()
//                        hideProgress()
                    }

                    SessionStatus.SUCCESS -> {
//                        hideProgress()
                        onSuccess.invoke()
                    }

                    SessionStatus.IDLE -> {}
                }
            }
        }
    }
}

@Composable
fun Fragment.ObserveSessionStatusLiveData(
    state: State<SessionStatus?>,
    shouldCloseTheViewOnApiError: Boolean = false,
    onLoading: () -> Unit = { },
    messageOnError: String? = null,
    onFinishLoading: () -> Unit = { },
    onSuccess: @Composable () -> Unit,
) {
    if (state.value is SessionStatus.LOADING) {
        onLoading()
    } else {
        onFinishLoading()
    }
    when (state.value) {
        SessionStatus.ERROR -> {
//            hideProgress()
            if (messageOnError == null) {
                showErrorApi()
            } else {
                showErrorApi(messageBody = messageOnError)
            }
        }

        SessionStatus.IDLE -> {
        }

        SessionStatus.LOADING -> {
//            showProgress()
        }

        SessionStatus.NETWORKERROR -> {
//            hideProgress()
            showErrorNetwork(shouldCloseTheViewOnApiError)
        }

        SessionStatus.SUCCESS -> {
//            hideProgress()
            onSuccess()
        }

        null -> {}
    }
}

@Composable
fun <T> GenericResultStateV2(
    state: State<ApiState<T>?>,
    isLoading: MutableState<Boolean>,
    onSuccess: @Composable (data: T) -> Unit,
) {
    isLoading.value = state.value is ApiState.Loading
    Log.w("STATE", isLoading.value.toString())
    when (state.value) {
        is ApiState.Error -> {

        }

        is ApiState.ErrorNetwork -> {

        }

        is ApiState.Loading -> {

        }

        is ApiState.NoData -> {

        }

        is ApiState.Success -> {
            state.value?.data?.let { dataResponse ->
                onSuccess(dataResponse)
            }
        }

        null -> {}
    }
}