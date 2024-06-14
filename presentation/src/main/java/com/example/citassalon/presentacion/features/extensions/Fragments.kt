package com.example.citassalon.presentacion.features.extensions

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.example.citassalon.R
import com.example.citassalon.presentacion.features.MainActivity
import com.example.citassalon.presentacion.main.AlertDialogs

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

fun Fragment.navigateAction(action: NavDirections) {
    val navController = this.findNavController()
    if (navController.currentDestination?.getAction(action.actionId) == null) {
        return
    } else {
        navController.navigate(action)
    }
}