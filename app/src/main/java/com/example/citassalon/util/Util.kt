package com.example.citassalon.util

import android.text.TextUtils
import android.util.Patterns
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
        price+=it.price
    }
    return price
}