package com.example.citassalon.util

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Context
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.os.Build
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import androidx.annotation.ColorRes
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.citassalon.ui.fecha.DatePickerFragment
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

fun View.navigate(accion: Int) {
    findNavController().navigate(accion)
}

fun Fragment.navigate(accion: Int) {
    findNavController().navigate(accion)
}

fun Fragment.navigate(accion: NavDirections) {
    findNavController().navigate(accion)
}

fun View.navigate(accion: NavDirections) {
    findNavController().navigate(accion)
}

fun Fragment.hideKeyboard() {
    view?.let { activity?.hideKeyboard(it) }
}

fun Activity.hideKeyboard() {
    hideKeyboard(currentFocus ?: View(this))
}

fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}

fun showDatePickerDialog(
    listener: DatePickerDialog.OnDateSetListener,
    fragment: Fragment,
    setMinDate: Boolean = false
) {
    val newFragment =
        DatePickerFragment.newInstance(listener, fragment.requireContext(), setMinDate)
    fragment.activity?.let { newFragment.show(it.supportFragmentManager, "datePicker") }
}

fun ImageView.setColorFilterImage(context: Context, @ColorRes colorInt: Int) {
    this.setColorFilter(
        ContextCompat.getColor(context, colorInt),
        PorterDuff.Mode.SRC_OVER
    )
}

@RequiresApi(Build.VERSION_CODES.M)
fun Drawable.tint(context: Context, @ColorRes color: Int) {
    DrawableCompat.setTint(this, context.resources.getColor(color, context.theme))
}



