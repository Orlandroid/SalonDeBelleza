package com.example.citassalon.presentacion.ui.extensions

import android.util.Log
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.example.citassalon.R
import com.example.domain.state.ApiState
import com.example.citassalon.presentacion.main.AlertDialogs
import com.example.citassalon.presentacion.ui.MainActivity

fun Fragment.showProgress() {
    (requireActivity() as MainActivity).showProgress()
}

fun Fragment.hideProgress() {
    (requireActivity() as MainActivity).hideProgress()
}

fun Fragment.shouldShowProgress(isLoading: Boolean) {
    (requireActivity() as MainActivity).shouldShowProgress(isLoading)
}


fun Fragment.showSuccessMessage(messageSuccess: String = getString(R.string.message_succes)) {
    val dialog = AlertDialogs(
        kindOfMessage = AlertDialogs.SUCCESS_MESSAGE,
        messageBody = messageSuccess
    )
    activity?.let { dialog.show(it.supportFragmentManager, "alertMessage") }
}

fun Fragment.showErrorApi(
    shouldCloseTheViewOnApiError: Boolean = false,
    messageBody: String = getString(R.string.error_al_obtener_datos)
) {
    val dialog = AlertDialogs(
        kindOfMessage = AlertDialogs.ERROR_MESSAGE,
        messageBody = messageBody,
        clickOnAccept = object : AlertDialogs.ClickOnAccept {
            override fun clickOnAccept() {
                if (shouldCloseTheViewOnApiError) {
                    findNavController().popBackStack()
                }
            }

            override fun clickOnCancel() {

            }

        })
    activity?.let { dialog.show(it.supportFragmentManager, "alertMessage") }
}

fun Fragment.showErrorNetwork(shouldCloseTheViewOnApiError: Boolean = false) {
    val dialog =
        AlertDialogs(
            kindOfMessage = AlertDialogs.ERROR_MESSAGE,
            messageBody = getString(R.string.error_internet),
            clickOnAccept = object : AlertDialogs.ClickOnAccept {
                override fun clickOnAccept() {
                    if (shouldCloseTheViewOnApiError) {
                        findNavController().popBackStack()
                    }
                }

                override fun clickOnCancel() {

                }
            }
        )
    activity?.let { dialog.show(it.supportFragmentManager, "alertMessage") }
}

fun Fragment.hideKeyboard() {
    view?.let { activity?.hideKeyboard(it) }
}

fun Fragment.packageName() = context?.packageName.toString()

fun Fragment.getUserMoneyFormat(money: Int) = "$ $money"

fun Fragment.navigate(accion: Int) {
    findNavController().navigate(accion)
}

fun Fragment.navigate(accion: NavDirections) {
    findNavController().navigate(accion)
}

fun Any?.makeSaveAction(action: () -> Unit) {
    if (this == null) {
        Log.w("ERROR", "Can,t make action")
        return
    }
    if (this is String) {
        if (this == "null") {
            return
        }
    }
    action()
}

fun NavController.navigateSafe(@IdRes destinationId: Int, navDirection: NavDirections) {
    if (currentDestination?.id == destinationId) {
        navigate(navDirection)
    }
}

fun Fragment.navigateAction(action: NavDirections) {
    val navController = this.findNavController()
    if (navController.currentDestination?.getAction(action.actionId) == null) {
        return
    } else {
        navController.navigate(action)
    }
}

fun NavController.navigateSafe(
    navDirections: NavDirections? = null
) {
    try {
        navDirections?.let {
            this.navigate(navDirections)
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
}


fun <T> Fragment.observeApiResultGeneric(
    liveData: LiveData<ApiState<T>>,
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
        if (hasProgressTheView) {
            shouldShowProgress(isLoading)
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