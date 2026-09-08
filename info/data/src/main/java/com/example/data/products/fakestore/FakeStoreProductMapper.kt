package com.example.data.products.fakestore

import com.example.domain.Product
import kotlin.math.roundToLong


fun FakeStoreProduct.toDomain(): Product {
    return Product(
        id = id,
        title = title,
        description = description,
        price = (price).roundToLong(),
        rating = rating.rate,
        image = image,
        category = category
    )
}