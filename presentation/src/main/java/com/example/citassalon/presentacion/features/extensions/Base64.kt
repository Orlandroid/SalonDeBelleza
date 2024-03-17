package com.example.citassalon.presentacion.features.extensions

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.IOException

fun Bitmap.toBase64(): String {
    var result = ""
    val baos = ByteArrayOutputStream()
    try {
        compress(Bitmap.CompressFormat.JPEG, 100, baos)
        baos.flush()
        baos.close()
        val bitmapBytes = baos.toByteArray()
        result = Base64.encodeToString(bitmapBytes, Base64.DEFAULT)
    } catch (e: IOException) {
        e.printStackTrace()
    } finally {
        try {
            baos.flush()
            baos.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
    return result
}

fun String.base64StringToBitmap(): Bitmap {
    val baos = ByteArrayOutputStream()
    var imageBytes: ByteArray = baos.toByteArray()
    imageBytes = Base64.decode(this, Base64.DEFAULT)
    return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
}

fun File.getImageBase64FromFile(): String {
    val stream = ByteArrayOutputStream()
    val bitmap = BitmapFactory.decodeFile(absolutePath)
    bitmap.compress(Bitmap.CompressFormat.JPEG, 80, stream)
    val imageBytes = stream.toByteArray()
    return Base64.encodeToString(imageBytes, Base64.NO_WRAP)
}

