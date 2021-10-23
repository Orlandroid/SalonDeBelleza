package com.example.citassalon.util

import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar


fun View.displaySnack(
    message: String,
    length: Int = Snackbar.LENGTH_LONG,
    func: Snackbar.() -> Unit
) {
    val snackBar = Snackbar.make(this, message, length)
    snackBar.func()
    snackBar.show()
}

fun View.showSnack(message: String) {
    Snackbar.make(this, message, Snackbar.LENGTH_SHORT).show()
}

fun Snackbar.action(message: String, listener: (View) -> Unit) {
    setAction(message, listener)
}

/** This extension function is to use to navigate
 * is like do the next
 *  findNavController().navigate(R.id.action_establecimiento_to_sucursales2)
 * */
fun View.navigate(accion: Int) {
    findNavController().navigate(accion)
}

fun Fragment.navigate(accion: Int) {
    findNavController().navigate(accion)
}


/***
 *  this is like do the next
 *  findNavController().navigate(acction)
 *  but accion is NavDirections and this mean what
 *   the acction have args
 *  */
fun Fragment.navigate(accion: NavDirections) {
    findNavController().navigate(accion)
}

