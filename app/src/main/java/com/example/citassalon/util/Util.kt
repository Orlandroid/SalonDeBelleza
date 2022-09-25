package com.example.citassalon.util

import android.graphics.Color
import android.text.TextUtils
import android.util.Patterns
import com.example.citassalon.R
import com.example.citassalon.data.models.remote.Product


fun isValidEmail(target: CharSequence?): Boolean {
    return if (TextUtils.isEmpty(target)) {
        false
    } else {
        Patterns.EMAIL_ADDRESS.matcher(target).matches()
    }
}

fun parseColor(color: String): Int =
    Color.parseColor(color)

fun getTotalPrice(products: List<Product>): Double {
    var price = 0.0
    products.forEach {
        price += it.price
    }
    return price
}

fun getRandomNoDataImage(): Int =
    when ((1..2).random()) {
        1 -> {
            R.drawable.file
        }
        else -> R.drawable.data_error
    }

fun getRandomErrorNetworkImage(): Int =
    when ((1..3).random()) {
        1 -> {
            R.drawable.network_error
        }
        2 -> {
            R.drawable.network
        }

        else ->  R.drawable.error
    }