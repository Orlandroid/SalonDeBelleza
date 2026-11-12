package com.example.core.util

import android.content.ContentResolver
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import com.example.model.state.models.Currency

fun Context.uriToBitmap(uri: Uri): Bitmap? {
    val contentResolver: ContentResolver = contentResolver
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
        val source = ImageDecoder.createSource(contentResolver, uri)
        ImageDecoder.decodeBitmap(source)
    } else {
        val bitmap =
            contentResolver.openInputStream(uri)?.use { stream ->
                Bitmap.createBitmap(BitmapFactory.decodeStream(stream))
            }
        bitmap
    }
}

fun Long.toCurrencyString(currency: Currency): String =
    "$%,.2f %s".format(
        this.toDouble(),
        currency.name
    )

fun Double.toCurrencyString(currency: Currency): String =
    "$%,.2f %s".format(
        this,
        currency.name
    )

fun Bitmap.toBase64(quality: Int = 70): String {
    val outputStream = java.io.ByteArrayOutputStream()
    compress(Bitmap.CompressFormat.JPEG, quality, outputStream)
    val byteArray = outputStream.toByteArray()
    return android.util.Base64.encodeToString(byteArray, android.util.Base64.DEFAULT)
}

fun String.base64ToBitmap(): Bitmap? {
    return try {
        val decodedBytes = android.util.Base64.decode(this, android.util.Base64.DEFAULT)
        BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
    } catch (e: Exception) {
        null
    }
}
