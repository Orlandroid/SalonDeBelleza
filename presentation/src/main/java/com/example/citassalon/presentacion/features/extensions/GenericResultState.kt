package com.example.citassalon.presentacion.features.extensions

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.fragment.app.Fragment
import com.example.domain.state.ApiState


@Composable
fun <T> Fragment.GenericResultState(
    state: State<ApiState<T>?>,
    shouldCloseTheViewOnApiError: Boolean = false,
    onSuccess: @Composable () -> Unit,
) {
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