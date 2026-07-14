package com.example.data.remote.products.platzy

import com.example.domain.entities.remote.products.Product


fun ProductPlatzi.toDomain(): Product {
    return Product(
        id = id,
        title = title,
        description = description,
        price = price.toDouble(),
        image = images.firstOrNull(),
        category = category.name
    )
}