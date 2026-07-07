package com.example.data.remote.products.fakestore

import android.annotation.SuppressLint
import kotlinx.serialization.Serializable

@SuppressLint("UnsafeOptInUsageError")
@Serializable
data class FakeStoreProduct(
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
        fun dummyProduct() = FakeStoreProduct(
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
