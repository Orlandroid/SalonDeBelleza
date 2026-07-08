package com.example.data.remote.products.dummyjson

import com.example.domain.entities.remote.products.Product



fun ProductsResponse.toDomain(): List<Product> {
    return products.map { it.toDomain() }
}

fun ProductDummyJson.toDomain(): Product {
    return Product(
        id = id,
        title = title,
        description = description,
        price = price,
        rating = rating,
        image = images.firstOrNull() ?: thumbnail
    )
}