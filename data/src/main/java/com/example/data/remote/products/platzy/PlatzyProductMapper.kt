package com.example.data.remote.products.platzy

import com.example.domain.entities.remote.products.Product
import kotlin.math.roundToLong


fun ProductPlatzi.toDomain(): Product {
    return Product(
        id = id,
        title = title,
        description = description,
        price = (price.toDouble()).roundToLong() ,
        image = images.firstOrNull(),
        category = category.name
    )
}