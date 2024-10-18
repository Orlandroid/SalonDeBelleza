package com.example.citassalon.presentacion.features.extensions

import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import com.example.domain.state.ApiState

fun <T> Fragment.observeApiResultGeneric(
    liveData: LiveData<ApiState<T>>,
    isLoadingState: (Boolean) -> Unit = {},
    onLoading: () -> Unit = { },
    onFinishLoading: () -> Unit = { },
    hasProgressTheView: Boolean = false,
    shouldCloseTheViewOnApiError: Boolean = false,
    onError: (() -> Unit)? = null,
    noData: () -> Unit = {},
    onSuccess: (data: T) -> Unit,
) {
    liveData.observe(viewLifecycleOwner) { apiState ->
        fun handleStatusOnLoading(isLoading: Boolean) {
            if (isLoading) {
                onLoading()
            } else {
                onFinishLoading()
            }
        }

        val isLoading = apiState is ApiState.Loading
        isLoadingState(isLoading)
        if (hasProgressTheView) {
//            shouldShowProgress(isLoading)
        } else {
            handleStatusOnLoading(isLoading)
        }
        when (apiState) {
            is ApiState.Success -> {
                if (apiState.data != null) {
                    onSuccess(apiState.data!!)
                }
            }

            is ApiState.Error -> {
                if (onError == null) {
                    showErrorApi(shouldCloseTheViewOnApiError)
                } else {
                    onError()
                }
            }

            is ApiState.ErrorNetwork -> {
                showErrorNetwork(shouldCloseTheViewOnApiError)
            }

            is ApiState.NoData -> {
                noData()
            }

            else -> {}
        }
    }
}