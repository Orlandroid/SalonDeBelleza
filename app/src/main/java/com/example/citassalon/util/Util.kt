package com.example.citassalon.util

import android.text.TextUtils
import android.util.Patterns
import com.example.citassalon.R
import com.example.citassalon.data.models.Product


fun isValidEmail(target: CharSequence?): Boolean {
    return if (TextUtils.isEmpty(target)) {
        false
    } else {
        Patterns.EMAIL_ADDRESS.matcher(target).matches()
    }
}

fun getTotalPrice(products: List<Product>): Double {
    var price = 0.0
    products.forEach {
        price += it.price
    }
    return price
}

fun getRandomNoDataAnimation(): Int =
    when ((1..3).random()) {
        1 -> {
            R.raw.no_data_animation
        }
        2 -> {
            R.raw.no_data_available
        }

        else -> R.raw.no_data_found
    }

fun getRandomErrorNetworkAnimation(): Int =
    when ((1..3).random()) {
        1 -> {
            R.raw.no_internet_connection
        }
        2 -> {
            R.raw.connection_error
        }

        else -> R.raw.no_wifi_connection_error
    }