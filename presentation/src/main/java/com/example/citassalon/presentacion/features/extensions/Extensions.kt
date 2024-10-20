package com.example.citassalon.presentacion.features.extensions

import android.app.Activity
import android.app.DatePickerDialog
import android.content.ContentResolver
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import com.example.citassalon.presentacion.features.schedule_appointment.schedule.DatePickerFragment
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


fun View.navigate(accion: NavDirections) {
    findNavController().navigate(accion)
}

fun Activity.hideKeyboard() {
    hideKeyboard(currentFocus ?: View(this))
}

fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}

fun Context.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
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

fun Fragment.showLog(message: String) {
    val mTag = context?.packageName.plus(tag)
    Log.d(mTag, message)
}

fun Context.uriToBitmap(uri: Uri): Bitmap? {
    val contentResolver: ContentResolver = contentResolver
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
        val source = ImageDecoder.createSource(contentResolver, uri)
        ImageDecoder.decodeBitmap(source)
    } else {
        val bitmap = contentResolver.openInputStream(uri)?.use { stream ->
            Bitmap.createBitmap(BitmapFactory.decodeStream(stream))
        }
        bitmap
    }
}






