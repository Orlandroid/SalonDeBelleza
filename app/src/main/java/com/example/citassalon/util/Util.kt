package com.example.citassalon.util

import android.content.Context
import android.graphics.Color
import android.os.Environment
import android.text.TextUtils
import android.util.Patterns
import com.example.citassalon.data.models.remote.Product
import java.io.*
import java.net.URL
import java.util.*


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




fun createFile(context: Context, suffix: String?): File? {
    val timeStamp = Calendar.getInstance().timeInMillis.toString()
    val storageDir: File? = context.getExternalFilesDir(FILE_BROWSER_CACHE_DIR)
    return File.createTempFile(
        timeStamp,
        suffix,
        storageDir
    )
}