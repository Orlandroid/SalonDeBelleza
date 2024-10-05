package com.example.citassalon.presentacion.features.extensions

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import com.example.citassalon.presentacion.features.dialogs.ProgressDialog
import com.example.domain.state.ApiState

@Composable
fun <T> ObserveGenericState(
    state: State<ApiState<T>?>,
    onSuccess: @Composable (data: T) -> Unit,
) {
    when (state.value) {
        is ApiState.Loading -> {
            ProgressDialog()
        }

        is ApiState.Success -> {
            state.value?.data?.let {
                onSuccess.invoke(it)
            }
        }

        else -> {}
    }

}