package com.example.citassalon.presentacion.features.extensions

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


fun Any.toJson(): String {
    val gson = Gson()
    return gson.toJson(this)
}

inline fun <reified T> String.fromJson(): T {
    val gson = Gson()
    val type = object : TypeToken<T>() {}.type
    return gson.fromJson(this, type)
}


