package com.example.data.remote.products.fakestore

import kotlinx.serialization.Serializable


@Serializable
data class FakeStoreProduct(
    val id: Int,
    val title: String,
    val price: Double,
    val description: String,
    val category: String,
    val image: String,
    val rating: Double,
    var imageBase64: String? = ""
)