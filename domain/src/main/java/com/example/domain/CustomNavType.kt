package com.example.domain

import android.net.Uri
import android.os.Bundle
import androidx.navigation.NavType
import com.example.domain.entities.local.AppointmentObject
import com.example.domain.entities.remote.products.Product
import kotlinx.serialization.json.Json

object CustomNavType {


    val productType = object : NavType<Product>(
        isNullableAllowed = false
    ) {
        override fun get(bundle: Bundle, key: String): Product? {
            return Json.decodeFromString(bundle.getString(key) ?: return null)
        }

        override fun parseValue(value: String): Product {
            return Json.decodeFromString(Uri.decode(value))
        }

        override fun serializeAsValue(value: Product): String {
            return Uri.encode(Json.encodeToString(value))
        }

        override fun put(bundle: Bundle, key: String, value: Product) {
            bundle.putString(key, Json.encodeToString(value))
        }
    }


    val appointmentObject = object : NavType<AppointmentObject>(
        isNullableAllowed = false
    ) {
        override fun get(bundle: Bundle, key: String): AppointmentObject? {
            return Json.decodeFromString(bundle.getString(key) ?: return null)
        }

        override fun parseValue(value: String): AppointmentObject {
            return Json.decodeFromString(Uri.decode(value))
        }

        override fun serializeAsValue(value: AppointmentObject): String {
            return Uri.encode(Json.encodeToString(value))
        }

        override fun put(bundle: Bundle, key: String, value: AppointmentObject) {
            bundle.putString(key, Json.encodeToString(value))
        }
    }
}
