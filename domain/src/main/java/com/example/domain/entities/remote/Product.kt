package com.example.domain.entities.remote

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import androidx.navigation.NavType
import com.example.domain.entities.local.AppointmentObject
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@SuppressLint("UnsafeOptInUsageError")
@Serializable
data class Product(
    val id: Int,
    val title: String,
    val price: Double,
    val description: String,
    val category: String,
    val image: String,
    val rating: Rating,
    var imageBase64: String? = ""
) {
    companion object {
        fun dummyProduct() = Product(
            id = 1,
            title = "Product",
            price = 5.00,
            description = "Description",
            category = "Category",
            image = "",
            rating = Rating(rate = 2.0, count = 1),
            imageBase64 = ""
        )
    }
}

@SuppressLint("UnsafeOptInUsageError")
@Serializable
data class Rating(
    val rate: Double,
    val count: Int
)

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

