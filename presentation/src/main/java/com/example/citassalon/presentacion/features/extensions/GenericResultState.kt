package com.example.citassalon.presentacion.features.extensions

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
    onSuccess: @Composable () -> Unit,
) {
    if (state.value is ApiState.Loading) {
        Log.w("ANDORID", "LOADING")
        onLoading()
    } else {
        onFinishLoading()
        Log.w("ANDORID", "NOTLOADING")
    }
    when (state.value) {
        is ApiState.Error -> {
            hideProgress()
            showErrorApi()
        }

        is ApiState.ErrorNetwork -> {
            hideProgress()
            showErrorNetwork(shouldCloseTheViewOnApiError)
        }

        is ApiState.Loading -> {
            showProgress()
        }

        is ApiState.NoData -> {

        }

        is ApiState.Success -> {
            hideProgress()
            onSuccess()
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
                        hideProgress()
                    }

                    SessionStatus.LOADING -> {
                        showProgress()
                    }

                    SessionStatus.NETWORKERROR -> {
                        showErrorNetwork()
                        hideProgress()
                    }

                    SessionStatus.SUCCESS -> {
                        hideProgress()
                        onSuccess.invoke()
                    }

                    SessionStatus.IDLE -> {}
                }
            }
        }
    }
}