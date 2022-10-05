package com.example.citassalon.ui.extensions

import android.view.View
import android.widget.ImageView


fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}

fun View.invisible() {
    this.visibility = View.INVISIBLE
}

fun ImageView.changeDrawableColor(color: Int) {
    this.setColorFilter(resources.getColor(color))
}

fun View.click(click: () -> Unit) {
    setOnClickListener { click() }
}
