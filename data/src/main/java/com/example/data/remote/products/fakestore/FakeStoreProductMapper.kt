package com.example.data.remote.products.fakestore

import com.example.domain.entities.remote.products.Product
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