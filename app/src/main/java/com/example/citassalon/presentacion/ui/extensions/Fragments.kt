package com.example.citassalon.presentacion.ui.extensions

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.example.citassalon.R
import com.example.citassalon.domain.state.ApiState
import com.example.citassalon.presentacion.main.AlertDialogs
import com.example.citassalon.presentacion.ui.MainActivity

fun Fragment.showProgress() {
    (requireActivity() as MainActivity).showProgress()
}

fun Fragment.hideProgress() {
    (requireActivity() as MainActivity).hideProgress()
}

fun Fragment.showSuccessMessage(messageSuccess: String = getString(R.string.message_succes)) {
    val dialog = AlertDialogs(
        kindOfMessage = AlertDialogs.SUCCES_MESSAGE,
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

fun Fragment.navigate(accion: Int) {
    findNavController().navigate(accion)
}

fun Fragment.navigate(accion: NavDirections) {
    findNavController().navigate(accion)
}

fun Fragment.getPackageName(): String {
    return context?.applicationContext?.packageName.toString()
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


/**
@Param  haveTheViewProgress: Boolean
use true for show a  generic progress or
false for make another action pass for you on onLoading(),and
onFinishLoading() like show Simmers,Skeletons
 */
fun <T> Fragment.observeApiResultGeneric(
    liveData: LiveData<ApiState<T>>,
    onLoading: () -> Unit = { },
    onFinishLoading: () -> Unit = { },
    haveTheViewProgress: Boolean = true,
    shouldCloseTheViewOnApiError: Boolean = false,
    onError: (() -> Unit)? = null,
    noData: () -> Unit = {},
    onSuccess: (data: T) -> Unit,
) {
    liveData.observe(viewLifecycleOwner) { apiState ->
        if (apiState is ApiState.Loading) {
            if (haveTheViewProgress) {
                showProgress()
            } else {
                onLoading()
            }
        } else {
            if (haveTheViewProgress) {
                hideProgress()
            } else {
                onFinishLoading()
            }
        }
        when (apiState) {
            is ApiState.Success -> {
                if (apiState.data != null) {
                    onSuccess(apiState.data)
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