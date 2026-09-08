package com.example.data.products.mydummyapi

import com.example.domain.Product
import kotlin.math.roundToLong


fun ProductMyDummyApi.toDomain(): Product {
    return Product(
        id = id,
        title = name,
        description = description,
        price = (price.toDouble()).roundToLong(),
        image = image
    )
}