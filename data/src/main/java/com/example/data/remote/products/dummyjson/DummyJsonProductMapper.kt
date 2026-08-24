package com.example.data.remote.products.dummyjson

import com.example.domain.entities.remote.products.Product
import kotlin.math.roundToLong


fun ProductsResponse.toDomain(): List<Product> {
    return products.map { it.toDomain() }
}

fun ProductDummyJson.toDomain(): Product {
    return Product(
        id = id,
        title = title,
        description = description,
        price =(price).roundToLong(),
        rating = rating,
        image = images.firstOrNull() ?: thumbnail
    )
}