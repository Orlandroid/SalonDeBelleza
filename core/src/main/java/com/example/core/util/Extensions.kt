package com.example.core.util

import android.content.ContentResolver
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import com.example.domain.wallet.Currency


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

fun Long.toCurrencyString(currency: Currency): String {
    return "$%,.2f %s".format(
        this.toDouble(),
        currency.name
    )
}






